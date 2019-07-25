package com.logic.jellyfish.ui.map

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.track.AMapTrackClient
import com.amap.api.track.ErrorCode
import com.amap.api.track.TrackParam
import com.amap.api.track.query.model.*
import com.logic.jellyfish.R
import com.logic.jellyfish.data.EventObserver
import com.logic.jellyfish.databinding.MapActivityBinding
import com.logic.jellyfish.utils.Constants
import com.logic.jellyfish.utils.Constants.SHEN_ZHEN
import com.logic.jellyfish.utils.SimpleOnTrackLifecycleListener
import com.logic.jellyfish.utils.SimpleOnTrackListener
import com.logic.jellyfish.utils.ext.createViewModel
import com.logic.jellyfish.utils.ext.log
import com.logic.jellyfish.utils.ext.toast
import kotlinx.android.synthetic.main.map_activity.*

class MapActivity : AppCompatActivity() {

   private val viewModel: MapViewModel by lazy { createViewModel<MapViewModel>() }
   private lateinit var binding: MapActivityBinding

   // 地图相关的配置和服务
   private lateinit var aMap: AMap
   private lateinit var uiSettings: UiSettings
   private lateinit var aMapTrackClient: AMapTrackClient

   // 地图相关的判断和参数
   private var terminalId: Long = 0
   private var trackId: Long = 0
   private var uploadToTrack = false
   private var isServiceRunning: Boolean = false
   private var isGatherRunning: Boolean = false

   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this, R.layout.map_activity)
      binding.apply {
         viewmodel = viewModel
         lifecycleOwner = this@MapActivity
      }
      map_view.onCreate(savedInstanceState)

      initMap()
      initTrack()
   }

   private fun initMap() {
      aMap = map_view.map
      uiSettings = aMap.uiSettings

      aMap.moveCamera(CameraUpdateFactory.newCameraPosition(SHEN_ZHEN))

      val myLocationStyle = MyLocationStyle()
      myLocationStyle.strokeColor(android.R.color.transparent)
      myLocationStyle.radiusFillColor(android.R.color.transparent)
      aMap.myLocationStyle = myLocationStyle

      uiSettings.isMyLocationButtonEnabled = true
      uiSettings.isTiltGesturesEnabled = false

      aMap.isMyLocationEnabled = true
      aMap.moveCamera(CameraUpdateFactory.zoomTo(18f))
   }

   private fun initTrack() {
      aMapTrackClient = AMapTrackClient(this)
      viewModel.startService.observe(this, EventObserver {
         if (isServiceRunning) {
            aMapTrackClient.stopTrack(
               TrackParam(Constants.SERVICE_ID, terminalId),
               onTrackLifecycleListener
            )
         } else {
            startTrack()
         }
      })
      viewModel.startGather.observe(this, EventObserver {
         if (isGatherRunning) {
            aMapTrackClient.stopGather(onTrackLifecycleListener)
         } else {
            aMapTrackClient.trackId = trackId
            aMapTrackClient.startGather(onTrackLifecycleListener)
         }
      })
   }

   private fun startTrack() {
      // 查询终端信息
      aMapTrackClient.queryTerminal(
         QueryTerminalRequest(Constants.SERVICE_ID, Constants.TERMINAL_NAME),
         object : SimpleOnTrackListener() {
            // 查询终端信息回调
            override fun onQueryTerminalCallback(queryTerminalResponse: QueryTerminalResponse) {
               if (queryTerminalResponse.isSuccess) {
                  // 如果终端存在
                  if (queryTerminalResponse.isTerminalExist) {
                     terminalId = queryTerminalResponse.tid
                     // 是否要创建新的轨迹,还是在之前的轨迹的基础上继续上传
                     if (uploadToTrack) {
                        // 添加新的轨迹
                        aMapTrackClient.addTrack(AddTrackRequest(Constants.SERVICE_ID, terminalId),
                           object : SimpleOnTrackListener() {
                              // 添加新的轨迹回调
                              override fun onAddTrackCallback(addTrackResponse: AddTrackResponse) {
                                 if (addTrackResponse.isSuccess) {
                                    trackId = addTrackResponse.trid
                                    beginTrack()
                                 } else {
                                    toast("网络请求失败,${addTrackResponse.errorMsg}")
                                 }
                              }
                           })
                     }
                     // 不创建新的轨迹,直接在旧的轨迹的基础上继续添加
                     else {
                        beginTrack()
                     }
                  }
                  // 如果终端不存在,就创建新的终端
                  else {
                     aMapTrackClient.addTerminal(
                        AddTerminalRequest(Constants.TERMINAL_NAME, Constants.SERVICE_ID),
                        object : SimpleOnTrackListener() {
                           override fun onCreateTerminalCallback(addTerminalResponse: AddTerminalResponse) {
                              if (addTerminalResponse.isSuccess) {
                                 terminalId = addTerminalResponse.tid
                                 beginTrack()
                              } else {
                                 toast("网络请求失败,${addTerminalResponse.errorMsg}")
                              }
                           }
                        })
                  }
               } else {
                  toast("网络请求失败${queryTerminalResponse.errorMsg}")
               }
            }
         }
      )
   }

   private fun beginTrack() {
      val trackParam = TrackParam(Constants.SERVICE_ID, terminalId)
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         trackParam.notification = createNotification()
      }
      aMapTrackClient.startTrack(trackParam, onTrackLifecycleListener)
   }

   /**
    * 在8.0以上手机，如果app切到后台，系统会限制定位相关接口调用频率
    * 可以在启动轨迹上报服务时提供一个通知，这样Service启动时会使用该通知成为前台Service，可以避免此限制
    */
   @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
   private fun createNotification(): Notification {
      val builder: Notification.Builder
      builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val nm = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         val channel = NotificationChannel(
            "CHANNEL_ID_SERVICE_RUNNING",
            "app service",
            NotificationManager.IMPORTANCE_LOW
         )
         nm.createNotificationChannel(channel)
         Notification.Builder(applicationContext, "CHANNEL_ID_SERVICE_RUNNING")
      } else {
         Notification.Builder(applicationContext)
      }
      val nfIntent = Intent(this, MapActivity::class.java)
      nfIntent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
      builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
         .setSmallIcon(R.mipmap.ic_launcher)
         .setContentTitle("猎鹰sdk运行中")
         .setContentText("猎鹰sdk运行中")
      return builder.build()
   }


   private val onTrackLifecycleListener = object : SimpleOnTrackLifecycleListener() {
      override fun onBindServiceCallback(status: Int, msg: String) {
         log("onBindServiceCallback, status: $status, msg: $msg")
      }

      override fun onStartTrackCallback(status: Int, msg: String) {
         if (status == ErrorCode.TrackListen.START_TRACK_SUCEE || status == ErrorCode.TrackListen.START_TRACK_SUCEE_NO_NETWORK) {
            // 成功启动
            toast("启动服务成功")
            isServiceRunning = true
            updateBtnStatus()
         } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
            // 已经启动
            toast("服务已经启动")
            isServiceRunning = true
            updateBtnStatus()
         } else {
            toast("error onStartTrackCallback, status: $status, msg: $msg")
         }
      }

      override fun onStopTrackCallback(status: Int, msg: String) {
         if (status == ErrorCode.TrackListen.STOP_TRACK_SUCCE) {
            // 成功停止
            toast("停止服务成功")
            isServiceRunning = false
            isGatherRunning = false
            updateBtnStatus()
         } else {
            toast("error onStopTrackCallback, status: $status, msg: $msg")
         }
      }

      override fun onStartGatherCallback(status: Int, msg: String) {
         when (status) {
            ErrorCode.TrackListen.START_GATHER_SUCEE -> {
               toast("定位采集开启成功")
               isGatherRunning = true
               updateBtnStatus()
            }
            ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED -> {
               toast("定位采集已经开启")
               isGatherRunning = true
               updateBtnStatus()
            }
            else -> {
               toast("error onStartGatherCallback, status: $status, msg: $msg")
            }
         }
      }

      override fun onStopGatherCallback(status: Int, msg: String) {
         if (status == ErrorCode.TrackListen.STOP_GATHER_SUCCE) {
            toast("定位采集停止成功")
            isGatherRunning = false
            updateBtnStatus()
         } else {
            toast("error onStopGatherCallback, status: $status, msg: $msg")
         }
      }
   }

   private fun updateBtnStatus() {
   }

   override fun onResume() {
      super.onResume()
      map_view.onResume()
   }

   override fun onPause() {
      super.onPause()
      map_view.onPause()
   }

   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      map_view.onSaveInstanceState(outState)
   }

   override fun onDestroy() {
      super.onDestroy()
      map_view.onDestroy()
      if (isServiceRunning) {
         aMapTrackClient.stopTrack(
            TrackParam(Constants.SERVICE_ID, terminalId),
            onTrackLifecycleListener
         )
      }
   }

}
