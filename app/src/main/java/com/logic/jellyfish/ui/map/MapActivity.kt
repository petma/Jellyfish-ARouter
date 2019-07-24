package com.logic.jellyfish.ui.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.track.AMapTrackClient
import com.amap.api.track.OnTrackLifecycleListener
import com.amap.api.track.query.model.*
import com.logic.jellyfish.App
import com.logic.jellyfish.R
import com.logic.jellyfish.data.EventObserver
import com.logic.jellyfish.databinding.MapActivityBinding
import com.logic.jellyfish.utils.createViewModel
import com.logic.jellyfish.utils.log
import com.logic.jellyfish.utils.toast
import kotlinx.android.synthetic.main.map_activity.*

class MapActivity : AppCompatActivity() {

    private val viewModel: MapViewModel by lazy { createViewModel(MapViewModel::class.java) }
    private lateinit var binding: MapActivityBinding

    private lateinit var aMap: AMap
    private lateinit var uiSettings: UiSettings
    private lateinit var locationClient: AMapLocationClient
    private lateinit var locationClientOption: AMapLocationClientOption

    private lateinit var aMapTrackClient: AMapTrackClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.map_activity)
        binding.apply {
            viewmodel = viewModel
            lifecycleOwner = this@MapActivity
        }

        map_view.onCreate(savedInstanceState)
        initMap()
        initTrackClient()
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
    }

    private fun initMap() {
        aMap = map_view.map
        uiSettings = aMap.uiSettings

        aMap.moveCamera(CameraUpdateFactory.newCameraPosition(SHEN_ZHEN))

        val myLocationStyle = MyLocationStyle()
//        myLocationStyle.interval(2000)
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
        myLocationStyle.strokeColor(android.R.color.transparent)
        myLocationStyle.radiusFillColor(android.R.color.transparent)
        aMap.myLocationStyle = myLocationStyle

        uiSettings.isMyLocationButtonEnabled = true
        uiSettings.isTiltGesturesEnabled = false

        aMap.isMyLocationEnabled = true
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18f))
    }

    private fun initTrackClient() {
        aMapTrackClient = AMapTrackClient(this)
        viewModel.startService.observe(this, EventObserver {
            if (App.name != null && App.sid != null) {
                aMapTrackClient.queryTerminal(
                    QueryTerminalRequest(App.sid!!.toLong(), App.name!!),
                    MyOnTrackListener()
                )
            }
        })
        viewModel.startGather.observe(this, EventObserver {
            aMapTrackClient.startGather(MyOnTrackLifecycleListener())
        })
    }

    private fun initLocation() {
        locationClient = AMapLocationClient(applicationContext)
        locationClientOption = getDefaultOption()
        locationClient.setLocationOption(locationClientOption)
        locationClient.setLocationListener(locationListener)
        locationClient.startLocation()
    }

    private fun getDefaultOption(): AMapLocationClientOption {
        val option = AMapLocationClientOption()
        option.apply {
            // 可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            // 可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            isGpsFirst = false
            // 可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            httpTimeOut = 30000
            // 可选，设置定位间隔。默认为2秒
            interval = 2000
            // 可选，设置是否返回逆地理地址信息。默认是true
            isNeedAddress = true
            // 可选，设置是否单次定位。默认是false
            isOnceLocation = false
            // 可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            isOnceLocationLatest = false
            // 可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP)
            // 可选，设置是否使用传感器。默认是false
            isSensorEnable = false
            // 可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            isWifiScan = true
            // 可选，设置是否使用缓存定位，默认为true
            isLocationCacheEnable = true
            // 可选，设置
            geoLanguage = AMapLocationClientOption.GeoLanguage.DEFAULT
        }
        return option
    }

    private val locationListener: AMapLocationListener = AMapLocationListener { location ->
        aMap.moveCamera(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition(LatLng(location.latitude, location.longitude), 18f, 30f, 0f)
            )
        )
    }

    private inner class MyOnTrackListener : OnTrackListener {
        override fun onLatestPointCallback(p0: LatestPointResponse?) {
            log("onLatestPointCallback, p0: $p0")
        }

        override fun onCreateTerminalCallback(p0: AddTerminalResponse?) {
            log("onCreateTerminalCallback, p0: $p0")
        }

        override fun onQueryTrackCallback(queryTrackResponse: QueryTrackResponse?) {
            log("onQueryTrackCallback, p0: $queryTrackResponse")
        }

        override fun onDistanceCallback(p0: DistanceResponse?) {
            log("onDistanceCallback, p0: $p0")
        }

        override fun onQueryTerminalCallback(queryTerminalResponse: QueryTerminalResponse?) {
            queryTerminalResponse?.let {
                if (it.isSuccess) {
                    if (it.tid <= 0) {
                        toast("服务不存在,应先创建")
                    } else {
                        toast("服务已存在")
                    }
                } else {
                    toast("请求失败${it.errorMsg}")
                }
            }
            log("onQueryTerminalCallback, queryTerminalResponse: $queryTerminalResponse")
        }

        override fun onHistoryTrackCallback(p0: HistoryTrackResponse?) {
            log("onHistoryTrackCallback, p0: $p0")
        }

        override fun onParamErrorCallback(p0: ParamErrorResponse?) {
            log("onParamErrorCallback, p0: $p0")
        }

        override fun onAddTrackCallback(p0: AddTrackResponse?) {
            log("onAddTrackCallback, p0: $p0")
        }
    }

    private inner class MyOnTrackLifecycleListener : OnTrackLifecycleListener {
        override fun onStartGatherCallback(p0: Int, p1: String?) {
            log("onStartGatherCallback, p0: $p0, p1: $p1")
        }

        override fun onStopTrackCallback(p0: Int, p1: String?) {
            log("onStopTrackCallback, p0: $p0, p1: $p1")
        }

        override fun onBindServiceCallback(p0: Int, p1: String?) {
            log("onBindServiceCallback, p0: $p0, p1: $p1")
        }

        override fun onStopGatherCallback(p0: Int, p1: String?) {
            log("onStopGatherCallback, p0: $p0, p1: $p1")
        }

        override fun onStartTrackCallback(p0: Int, p1: String?) {
            log("onStartTrackCallback, p0: $p0, p1: $p1")
        }

    }

    companion object {

        private val LUJIAZUI = CameraPosition.Builder()
            .target(LatLng(31.238068, 121.501654)).zoom(18f).bearing(0f).tilt(0f).build()

        private val SHEN_ZHEN = CameraPosition.Builder()
            .target(LatLng(114.085947, 22.547)).zoom(18f).bearing(0f).tilt(0f).build()


        private const val MAP_FRAGMENT_TAG = "map"
    }
}
