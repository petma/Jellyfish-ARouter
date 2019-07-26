package com.logic.jellyfish.service

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import com.amap.api.track.AMapTrackClient
import com.amap.api.track.ErrorCode
import com.amap.api.track.TrackParam
import com.amap.api.track.query.model.*
import com.logic.jellyfish.R
import com.logic.jellyfish.data.MessageEvent
import com.logic.jellyfish.ui.timer.TimerActivity
import com.logic.jellyfish.utils.Constants
import com.logic.jellyfish.utils.SimpleOnTrackLifecycleListener
import com.logic.jellyfish.utils.SimpleOnTrackListener
import com.logic.jellyfish.utils.ext.log
import com.logic.jellyfish.utils.ext.toast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 轨迹上报服务,在跑步的时候手机一般是熄屏的,需要创建一个能稳定在后台运行的前台服务,
 * 以实时上报当前的位置到高德地图的服务器
 *
 * 在这里集成轨迹服务的开启和采集的开启
 * 在这里不需要绘制地图
 */
class TrackService : Service() {

   private lateinit var aMapTrackClient: AMapTrackClient

   // 地图相关的判断和参数
   private var terminalId: Long = 0
   private var trackId: Long = 0
   private var uploadToTrack = false
   private var isServiceRunning: Boolean = false
   private var isGatherRunning: Boolean = false

   override fun onCreate() {
      super.onCreate()
      EventBus.getDefault().register(this)
      aMapTrackClient = AMapTrackClient(applicationContext)
      startForeground(NOTIFICATION_ID, createNotification())
      startTrack()
   }

   override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
      return START_STICKY
   }

   override fun onDestroy() {
      super.onDestroy()
      EventBus.getDefault().unregister(this)
      if (isServiceRunning) {
         aMapTrackClient.stopTrack(
            TrackParam(Constants.SERVICE_ID, terminalId),
            onTrackLifecycleListener
         )
      }
   }

   override fun onBind(intent: Intent?): IBinder? {
      return null
   }

   @Subscribe(threadMode = ThreadMode.MAIN)
   fun onKeyInputEvent(event: MessageEvent) {
      when (event.type) {
         MessageEvent.TYPE_PAUSE_TRACK_SERVICE -> stopGather()
         MessageEvent.TYPE_RESUME_TRACK_SERVICE -> startGather()
      }
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
                        aMapTrackClient.addTrack(
                           AddTrackRequest(Constants.SERVICE_ID, terminalId),
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

   private fun startGather() {
      aMapTrackClient.trackId = trackId
      aMapTrackClient.startGather(onTrackLifecycleListener)
   }

   private fun stopGather() {
      aMapTrackClient.stopGather(onTrackLifecycleListener)
   }

   private fun beginTrack() {
      val trackParam = TrackParam(Constants.SERVICE_ID, terminalId)
      // 自己创建一个前台服务,就不让高德地图创建了
//      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//         trackParam.notification = createNotification()
//      }
      aMapTrackClient.startTrack(trackParam, onTrackLifecycleListener)
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
            // 成功启动服务后, 开始采集
            startGather()
         } else if (status == ErrorCode.TrackListen.START_TRACK_ALREADY_STARTED) {
            // 已经启动
            toast("服务已经启动")
            isServiceRunning = true
            // 成功启动服务后, 开始采集
            startGather()
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
         } else {
            toast("error onStopTrackCallback, status: $status, msg: $msg")
         }
      }

      override fun onStartGatherCallback(status: Int, msg: String) {
         when (status) {
            ErrorCode.TrackListen.START_GATHER_SUCEE -> {
               toast("定位采集开启成功")
               isGatherRunning = true
            }
            ErrorCode.TrackListen.START_GATHER_ALREADY_STARTED -> {
               toast("定位采集已经开启")
               isGatherRunning = true
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
         } else {
            toast("error onStopGatherCallback, status: $status, msg: $msg")
         }
      }
   }

   private fun createNotification(): Notification {
      val builder =
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
               NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME,
               NotificationManager.IMPORTANCE_HIGH
            )
            channel.lightColor = Color.YELLOW
            channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            service.createNotificationChannel(channel)
            Notification.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
         } else {
            Notification.Builder(applicationContext)
         }
      val intent = Intent(this, TimerActivity::class.java)
//      intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
      val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
      return builder.setContentIntent(pendingIntent)
         .setContentTitle("水母运动")
         .setContentText("正在后台定位")
         .setSmallIcon(R.mipmap.logo)
         .build()
   }

   companion object {
      private const val NOTIFICATION_ID = 100
      private const val NOTIFICATION_CHANNEL_ID = "hello"
      private const val NOTIFICATION_CHANNEL_NAME = "world"
   }
}