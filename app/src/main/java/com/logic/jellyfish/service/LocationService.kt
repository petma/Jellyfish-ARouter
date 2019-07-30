package com.logic.jellyfish.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.SystemClock
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.logic.jellyfish.R
import com.logic.jellyfish.data.entity.MessageEvent
import com.logic.jellyfish.data.entity.TimerEvent
import com.logic.jellyfish.data.room.RoomFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class LocationService : Service() {

   private var locationClient: AMapLocationClient? = null
   private var locationOption: AMapLocationClientOption? = null

   private var eventBus: EventBus? = null

   override fun onCreate() {
      super.onCreate()
      initLocation()
      startForeground(2001, buildNotification())

      eventBus = EventBus.getDefault()
      eventBus?.register(this)

      startCount()
   }

   override fun onDestroy() {
      super.onDestroy()
      locationClient?.stopLocation()
      eventBus?.unregister(this)
      eventBus = null
   }

   @Subscribe(threadMode = ThreadMode.MAIN)
   fun onMessageEvent(event: MessageEvent) {
      when (event.type) {
         MessageEvent.TYPE_PAUSE_TRACK_SERVICE -> stopCount()
         MessageEvent.TYPE_RESUME_TRACK_SERVICE -> startCount()
      }
   }

   /**
    * 初始化定位
    *
    * @since 2.8.0
    * @author hongming.wang
    */
   private fun initLocation() {
      //初始化client
      locationClient = AMapLocationClient(applicationContext)
      locationOption = getDefaultOption()
      //设置定位参数
      locationClient?.setLocationOption(locationOption)
      // 设置定位监听
      locationClient?.setLocationListener(locationListener)

      // 启动定位
      locationClient?.startLocation()
   }

   private var currSpeed: Float = 0F

   /**
    * 定位监听
    */
   private var locationListener: AMapLocationListener = AMapLocationListener { location ->
      if (location != null && location.errorCode == 0) {
         currSpeed = location.speed
         CoroutineScope(Dispatchers.Main).launch {
            RoomFactory.repository.insertLatLng(location.latitude, location.longitude)
         }
      }
   }

   private fun getDefaultOption(): AMapLocationClientOption {
      val mOption = AMapLocationClientOption()
      mOption.locationMode =
         AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
      mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
      mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
      mOption.interval = 5000//可选，设置定位间隔。默认为2秒
      mOption.isNeedAddress = true//可选，设置是否返回逆地理地址信息。默认是true
      mOption.isOnceLocation = false//可选，设置是否单次定位。默认是false
      mOption.isOnceLocationLatest = false//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
      AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
      mOption.isSensorEnable = false//可选，设置是否使用传感器。默认是false
      mOption.isWifiScan =
         true //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
      mOption.isLocationCacheEnable = true //可选，设置是否使用缓存定位，默认为true
      return mOption
   }

   private fun buildNotification(): Notification {
      val builder: Notification.Builder
      val notification: Notification
      if (android.os.Build.VERSION.SDK_INT >= 26) {
         //Android O上对Notification进行了修改，如果设置的targetSDKVersion>=26建议使用此种方式创建通知栏
         val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         val channelId = packageName
         val notificationChannel = NotificationChannel(
            channelId,
            "BackgroundLocation",
            NotificationManager.IMPORTANCE_DEFAULT
         ).apply {
            enableLights(true)//是否在桌面icon右上角展示小圆点
            lightColor = Color.BLUE //小圆点颜色
            setShowBadge(true) //是否在久按桌面图标时显示此渠道的通知
         }
         notificationManager.createNotificationChannel(notificationChannel)
         builder = Notification.Builder(applicationContext, channelId)
      } else {
         builder = Notification.Builder(applicationContext)
      }
      builder.setSmallIcon(R.mipmap.logo)
         .setContentTitle("水母运动")
         .setContentText("正在后台运行")
         .setWhen(System.currentTimeMillis())

      notification = builder.build()
      return notification
   }

   private var minute = 0
   private var second = 0
   private var targetTime = 0L
   private var timerHandler: Handler? = null

   private fun startCount() {
      if (second != 0) {
         second--
      }
      timerHandler = @SuppressLint("HandlerLeak") object : Handler() {
         override fun handleMessage(msg: Message?) {
            eventBus?.post(TimerEvent(minute, second, currSpeed))
            if (second >= 59) {
               second = 0
               minute++
            } else {
               second++
            }
            targetTime += 1000L
            sendEmptyMessageAtTime(0, targetTime)
         }
      }
      targetTime = SystemClock.uptimeMillis()
      timerHandler?.sendEmptyMessage(0)
   }

   private fun stopCount() {
      timerHandler?.removeMessages(0)
      timerHandler = null
   }

   override fun onBind(intent: Intent?): IBinder? {
      return null
   }

}