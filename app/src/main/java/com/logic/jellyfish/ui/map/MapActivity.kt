package com.logic.jellyfish.ui.map

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.trace.LBSTraceClient
import com.amap.api.trace.TraceListener
import com.amap.api.track.AMapTrackClient
import com.amap.api.track.query.entity.DriveMode
import com.amap.api.track.query.entity.Point
import com.amap.api.track.query.model.*
import com.logic.jellyfish.Cache
import com.logic.jellyfish.R
import com.logic.jellyfish.data.room.RoomFactory
import com.logic.jellyfish.databinding.ActivityMapBinding
import com.logic.jellyfish.utils.Constants
import com.logic.jellyfish.utils.SimpleOnTrackListener
import com.logic.jellyfish.utils.Utils
import com.logic.jellyfish.utils.ext.createViewModel
import com.logic.jellyfish.utils.ext.toast
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * 功能:
 *
 * 1. 实时显示自己的位置
 * 2. 画出跑步的轨迹
 * 3. 截屏分享
 */
class MapActivity : AppCompatActivity(), TraceListener {

  private val viewModel: MapViewModel by lazy { createViewModel<MapViewModel>() }
  private lateinit var binding: ActivityMapBinding

  // 地图相关的配置和服务
  private lateinit var aMap: AMap
  private lateinit var aMapTrackClient: AMapTrackClient

  // 画轨迹相关的参数,这个貌似只是记录,用来存储到服务器或者上报
  private val polyLines = LinkedList<Polyline>()
  private val endMarkers = LinkedList<Marker>()

  private var graspStartMarker: Marker? = null
  private var graspEndMarker: Marker? = null
  private var graspRoleMarker: Marker? = null
  private var graspPolyline: Polyline? = null
  private var mGraspLatLngList: List<LatLng>? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_map)
    binding.apply {
      viewmodel = viewModel
      lifecycleOwner = this@MapActivity
    }
    map_view.onCreate(savedInstanceState)

    init()
  }

  private fun init() {
    initMap()
    if (Cache.isLocationSDK)
      paintTrack()
    else
      getQueryTrack()
  }

  private fun initMap() {
    aMapTrackClient = AMapTrackClient(applicationContext)
    aMap = map_view.map

    // CustomMapStyleOptions,设置自定义地图
    CustomMapStyleOptions().apply {
      isEnable = true
      styleDataPath = "style.data"
      styleExtraPath = "style_extra.data"
      styleId = "10886f9729a3dd6f8dca19f010d5376c"
      aMap.setCustomMapStyle(this)
    }

    // MyLocationStyle,设置定位小蓝点相关的设置
    MyLocationStyle().apply {
      strokeColor(android.R.color.transparent)
      radiusFillColor(android.R.color.transparent)
      myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
      interval(2000)
      aMap.myLocationStyle = this
      aMap.isMyLocationEnabled = true
    }

    // UiSettings,设置地图交互
    aMap.uiSettings.apply {
      // 默认定位按钮是否显示，非必需设置
      isMyLocationButtonEnabled = true
      // 是否可以倾斜
      isTiltGesturesEnabled = false
    }

    // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    aMap.moveCamera(CameraUpdateFactory.zoomTo(18f))
  }

  private fun paintTrack() {
    val traceClient = LBSTraceClient(applicationContext)
    lifecycleScope.launch {
      val pathRecords = RoomFactory.repository.getPathRecords()
      val traceLocations = Utils.parseTraceLocationList(pathRecords)
      traceClient.queryProcessedTrace(1, traceLocations, LBSTraceClient.TYPE_AMAP, this@MapActivity)
    }
  }

  override fun onRequestFailed(p0: Int, p1: String?) {
    toast("轨迹纠偏失败")
  }

  override fun onTraceProcessing(p0: Int, p1: Int, list: MutableList<LatLng>) {
  }

  override fun onFinished(p0: Int, list: MutableList<LatLng>, p2: Int, p3: Int) {
    toast("轨迹纠偏完成")
    addGraspTrace(list)
    mGraspLatLngList = list
  }

  /**
   * 地图上添加纠偏后轨迹线路及起终点、轨迹动画小人
   *
   */
  private fun addGraspTrace(graspList: List<LatLng>?) {
    if (graspList == null || graspList.size < 2) {
      return
    }
    val startPoint = graspList[0]
    val endPoint = graspList[graspList.size - 1]

    graspPolyline = aMap.addPolyline(
      PolylineOptions()
        .setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.grasp_trace_line))
        .width(40f).addAll(graspList)
    )

    graspStartMarker = aMap.addMarker(
      MarkerOptions().position(startPoint).icon(
        BitmapDescriptorFactory.fromResource(R.drawable.start)
      )
    )

    graspEndMarker = aMap.addMarker(
      MarkerOptions().position(endPoint).icon(
        BitmapDescriptorFactory.fromResource(R.drawable.end)
      )
    )

    graspRoleMarker = aMap.addMarker(
      MarkerOptions().position(startPoint).icon(
        BitmapDescriptorFactory.fromBitmap(
          BitmapFactory.decodeResource(
            resources, R.drawable.walk
          )
        )
      )
    )
  }


  //  private fun paintTrack() {
//    lifecycleScope.launch {
//      val latLngs = RoomFactory.repository.getOptimizedLatLngs()
//      if (latLngs.isNotEmpty()) {
//        val polyLine =
//          aMap.addPolyline(PolylineOptions().addAll(latLngs).color(Color.parseColor("#FFC125")))
//        polyLine?.isVisible = true
//        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(getBounds(latLngs), 30))
//      }
//    }
//  }
//
//  private fun getBounds(pointList: List<LatLng>): LatLngBounds {
//    val builder = LatLngBounds.builder()
//    for (i in pointList.indices) {
//      builder.include(pointList[i])
//    }
//    return builder.build()
//  }

  /**
   * 获得特定轨迹的点,不包括散点
   */
  private fun getQueryTrack() {
    clearTracksOnMap()
    // 先查询terminal id，然后用terminal id查询轨迹
    // 查询符合条件的所有轨迹，并分别绘制
    aMapTrackClient.queryTerminal(
      QueryTerminalRequest(
        Constants.SERVICE_ID,
        Cache.terminalName
      ), object : SimpleOnTrackListener() {
        override fun onQueryTerminalCallback(queryTerminalResponse: QueryTerminalResponse) {
          if (queryTerminalResponse.isSuccess) {
            if (queryTerminalResponse.isTerminalExist) {
              // 搜索最近12小时以内上报的属于某个轨迹的轨迹点信息，散点上报不会包含在该查询结果中
              // http://a.amap.com/lbs/static/unzip/Android_Track_Doc/index.html
              val queryTrackRequest = QueryTrackRequest(
                Constants.SERVICE_ID, // sid - 服务ID
                queryTerminalResponse.tid, // tid - 终端ID
                Cache.trackId, // trid - 轨迹id，不指定，查询所有轨迹，注意分页仅在查询特定轨迹id时生效，查询所有轨迹时无法对轨迹点进行分页
                System.currentTimeMillis() - 12 * 60 * 60 * 1000, // startTime - 12个小时前的时间戳
                System.currentTimeMillis(), // endTime - 现在的时间戳
                1, // denoise - 去噪
                0, // mapmatch - 绑路
                0, // threshold - 不进行精度过滤
                DriveMode.WALKING, // drivemode - 当前仅支持驾车模式
                0, // recoup - 距离补偿
                5000, // gap -  距离补偿，只有超过5km的点才启用距离补偿
                1, // ispoints - 结果应该包含轨迹点信息
                1, // page - 返回第1页数据，但由于未指定轨迹，分页将失效
                100    // pageSize - 一页不超过100条
              )
              aMapTrackClient.queryTerminalTrack(
                queryTrackRequest,
                object : SimpleOnTrackListener() {
                  override fun onQueryTrackCallback(queryTrackResponse: QueryTrackResponse) {
                    if (queryTrackResponse.isSuccess) {
                      val tracks = queryTrackResponse.tracks
                      if (tracks != null && tracks.isNotEmpty()) {
                        var allEmpty = true
                        for (track in tracks) {
                          val points = track.points
                          if (points != null && points.size > 0) {
                            allEmpty = false
                            drawTrackOnMap(points)
                          }
                        }
                        if (allEmpty) {
                          toast("所有轨迹都无轨迹点，请尝试放宽过滤限制，如：关闭绑路模式")
                        } else {
                          val sb = StringBuilder()
                          sb.append("共查询到").append(tracks.size)
                            .append("条轨迹，每条轨迹行驶距离分别为：")
                          for (track in tracks) {
                            sb.append(track.distance).append("m,")
                          }
                          sb.deleteCharAt(sb.length - 1)
                          toast(sb.toString())
                        }
                      } else {
                        toast("未获取到轨迹")
                      }
                    } else {
                      toast("查询历史轨迹失败，${queryTrackResponse.errorMsg}")
                    }
                  }
                })
            } else {
              toast("Terminal不存在")
            }
          } else {
            toast("网络请求失败，错误原因: ${queryTerminalResponse.errorMsg}")
          }
        }
      })
  }

  /**
   * 获得历史最近12个小时所有上报的点,包括散点
   */
  private fun getHistoryTrack() {
    clearTracksOnMap()
    // 先查询terminal id，然后用terminal id查询轨迹
    // 查询符合条件的所有轨迹点，并绘制
    aMapTrackClient.queryTerminal(
      QueryTerminalRequest(Constants.SERVICE_ID, Cache.terminalName),
      object : SimpleOnTrackListener() {
        override fun onQueryTerminalCallback(queryTerminalResponse: QueryTerminalResponse) {
          if (queryTerminalResponse.isSuccess) {
            if (queryTerminalResponse.isTerminalExist) {
              // 搜索最近12小时以内上报的轨迹
              val historyTrackRequest = HistoryTrackRequest(
                Constants.SERVICE_ID,
                queryTerminalResponse.tid,
                System.currentTimeMillis() - 12 * 60 * 60 * 1000,
                System.currentTimeMillis(),
                0, // todo 绑路
                0, // todo 驾车补偿
                5000, // 距离补偿，只有超过5km的点才启用距离补偿
                0, // 由旧到新排序
                1, // 返回第1页数据
                100, // 一页不超过100条
                ""  // 暂未实现，该参数无意义，请留空
              )
              aMapTrackClient.queryHistoryTrack(
                historyTrackRequest,
                object : SimpleOnTrackListener() {
                  override fun onHistoryTrackCallback(historyTrackResponse: HistoryTrackResponse) {
                    if (historyTrackResponse.isSuccess) {
                      val historyTrack = historyTrackResponse.historyTrack
                      if (historyTrack == null || historyTrack.count == 0) {
                        toast("未获取到轨迹点")
                        return
                      }
                      val points = historyTrack.points
                      drawTrackOnMap(points)
                    } else {
                      toast("查询历史轨迹点失败, ${historyTrackResponse.errorMsg}")
                    }
                  }
                })
            } else {
              toast("Terminal不存在")
            }
          } else {
            toast("网络请求失败，错误原因: ${queryTerminalResponse.errorMsg}")
          }
        }
      })
  }

  private fun drawTrackOnMap(points: List<Point>) {
    val boundsBuilder = LatLngBounds.Builder()

    val polylineOptions = PolylineOptions()
    polylineOptions.color(Color.BLUE).width(20f)

    // 画起点
    if (points.isNotEmpty()) {
      val p = points[0]
      val latLng = LatLng(p.lat, p.lng)
      val markerOptions = MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
      endMarkers.add(aMap.addMarker(markerOptions))
    }

    // 画终点
    if (points.size > 1) {
      val p = points[points.size - 1]
      val latLng = LatLng(p.lat, p.lng)
      val markerOptions = MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
      endMarkers.add(aMap.addMarker(markerOptions))
    }

    // 画线
    for (p in points) {
      val latLng = LatLng(p.lat, p.lng)
      polylineOptions.add(latLng)
      boundsBuilder.include(latLng)
    }

    val polyline = aMap.addPolyline(polylineOptions)
    polyLines.add(polyline)
    aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30))
  }

  private fun clearTracksOnMap() {
    for (polyline in polyLines) {
      polyline.remove()
    }
    for (marker in endMarkers) {
      marker.remove()
    }
    endMarkers.clear()
    polyLines.clear()
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
}
