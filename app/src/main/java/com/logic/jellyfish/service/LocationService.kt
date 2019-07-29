package com.logic.jellyfish.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.logic.jellyfish.data.room.RoomFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationService : Service() {

   private lateinit var locationClient: AMapLocationClient
   private lateinit var locationOption: AMapLocationClientOption

   override fun onCreate() {
      super.onCreate()
      initLocation()
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
      locationClient.setLocationOption(locationOption)
      // 设置定位监听
      locationClient.setLocationListener(locationListener)

//      CoroutineScope(Dispatchers.Main).launch {
//         RoomFactory.repository.deleteLatLngs()
//      }

      // 启动定位
      locationClient.startLocation()

   }

   /**
    * 定位监听
    */
   private var locationListener: AMapLocationListener = AMapLocationListener { location ->
      if (location != null && location.errorCode == 0) {
         CoroutineScope(Dispatchers.Main).launch {
            RoomFactory.repository.insertLatLng(location.longitude, location.latitude)
         }
      }
   }

   /**
    * 默认的定位参数
    * @since 2.8.0
    * @author hongming.wang
    */
   private fun getDefaultOption(): AMapLocationClientOption {
      val mOption = AMapLocationClientOption()
      mOption.locationMode =
         AMapLocationClientOption.AMapLocationMode.Hight_Accuracy//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
      mOption.isGpsFirst = false//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
      mOption.httpTimeOut = 30000//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
      mOption.interval = 2000//可选，设置定位间隔。默认为2秒
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


   override fun onBind(intent: Intent?): IBinder? {
      return null
   }

}