package com.logic.jellyfish.ui.map

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.amap.api.maps.model.LatLngBounds
import com.amap.api.track.AMapTrackClient
import com.amap.api.track.query.entity.DriveMode
import com.amap.api.track.query.entity.Point
import com.amap.api.track.query.model.QueryTerminalRequest
import com.amap.api.track.query.model.QueryTerminalResponse
import com.amap.api.track.query.model.QueryTrackRequest
import com.amap.api.track.query.model.QueryTrackResponse
import com.logic.jellyfish.Cache
import com.logic.jellyfish.R
import com.logic.jellyfish.data.room.RoomFactory
import com.logic.jellyfish.databinding.MapActivityBinding
import com.logic.jellyfish.utils.Constants
import com.logic.jellyfish.utils.Constants.SHEN_ZHEN
import com.logic.jellyfish.utils.SimpleOnTrackListener
import com.logic.jellyfish.utils.ext.createViewModel
import com.logic.jellyfish.utils.ext.toast
import kotlinx.android.synthetic.main.map_activity.*
import kotlinx.coroutines.launch
import java.util.*

/**
 * 功能:
 *
 * 1. 实时显示自己的位置
 * 2. 画出跑步的轨迹
 * 3. 截屏分享
 */
class MapActivity : AppCompatActivity() {

  private val viewModel: MapViewModel by lazy { createViewModel<MapViewModel>() }
  private lateinit var binding: MapActivityBinding

  // 地图相关的配置和服务
  private var aMap: AMap? = null
  private var uiSettings: UiSettings? = null
  private var aMapTrackClient: AMapTrackClient? = null

  // 画轨迹相关的参数
  private val polyLines = LinkedList<Polyline>()
  private val endMarkers = LinkedList<Marker>()


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = DataBindingUtil.setContentView(this, R.layout.map_activity)
    binding.apply {
      viewmodel = viewModel
      lifecycleOwner = this@MapActivity
    }
    map_view.onCreate(savedInstanceState)

    init()
    paintTrack()
  }

  private fun init() {
    aMapTrackClient = AMapTrackClient(applicationContext)
    aMap = map_view.map
    uiSettings = aMap?.uiSettings

    aMap?.moveCamera(CameraUpdateFactory.newCameraPosition(SHEN_ZHEN))

    val myLocationStyle = MyLocationStyle()
    myLocationStyle.strokeColor(android.R.color.transparent)
    myLocationStyle.radiusFillColor(android.R.color.transparent)
    aMap?.myLocationStyle = myLocationStyle

    uiSettings?.isMyLocationButtonEnabled = true
    uiSettings?.isTiltGesturesEnabled = false

    aMap?.isMyLocationEnabled = true
    aMap?.moveCamera(CameraUpdateFactory.zoomTo(18f))
  }

  private fun paintTrack() {
    lifecycleScope.launch {
      val latLngs = RoomFactory.repository.getOptimizedLatLngs()
      if (latLngs.isNotEmpty()) {
        val polyLine =
          aMap?.addPolyline(PolylineOptions().addAll(latLngs).color(Color.parseColor("#FFC125")))
        polyLine?.isVisible = true
        aMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(getBounds(latLngs), 30))
      }
    }
  }

  private fun getBounds(pointList: List<LatLng>): LatLngBounds {
    val builder = LatLngBounds.builder()
    for (i in pointList.indices) {
      builder.include(pointList[i])
    }
    return builder.build()

  }


  private fun Track() {
    clearTracksOnMap()
    // 先查询terminal id，然后用terminal id查询轨迹
    // 查询符合条件的所有轨迹，并分别绘制
    aMapTrackClient?.queryTerminal(
      QueryTerminalRequest(
        Constants.SERVICE_ID,
        Cache.terminalName
      ), object : SimpleOnTrackListener() {
        override fun onQueryTerminalCallback(queryTerminalResponse: QueryTerminalResponse) {
          if (queryTerminalResponse.isSuccess) {
            if (queryTerminalResponse.isTerminalExist) {
              val tid = queryTerminalResponse.tid
              // 搜索最近12小时以内上报的属于某个轨迹的轨迹点信息，散点上报不会包含在该查询结果中
              // http://a.amap.com/lbs/static/unzip/Android_Track_Doc/index.html
              val queryTrackRequest = QueryTrackRequest(
                Constants.SERVICE_ID, // sid - 服务ID
                tid, // tid - 终端ID
                -1, // trid - 轨迹id，不指定，查询所有轨迹，注意分页仅在查询特定轨迹id时生效，查询所有轨迹时无法对轨迹点进行分页
                System.currentTimeMillis() - 12 * 60 * 60 * 1000, // startTime - 12个小时前的时间戳
                System.currentTimeMillis(), // endTime - 现在的时间戳
                1, // denoise - 不启用去噪
                1, // mapmatch - 绑路 1是 0否
                0, // threshold - 不进行精度过滤
                DriveMode.WALKING, // drivemode - 当前仅支持驾车模式
                1, // recoup - 距离补偿 1是 0否
                5000, // gap -  距离补偿，只有超过5km的点才启用距离补偿
                1, // ispoints - 结果应该包含轨迹点信息
                1, // page - 返回第1页数据，但由于未指定轨迹，分页将失效
                100    // pageSize - 一页不超过100条
              )
              aMapTrackClient?.queryTerminalTrack(
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

  private fun drawTrackOnMap(points: List<Point>) {
    val boundsBuilder = LatLngBounds.Builder()
    val polylineOptions = PolylineOptions()
    polylineOptions.color(Color.BLUE).width(20f)
    if (points.isNotEmpty()) {
      // 起点
      val p = points[0]
      val latLng = LatLng(p.lat, p.lng)
      val markerOptions = MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
      endMarkers.add(aMap!!.addMarker(markerOptions))
    }
    if (points.size > 1) {
      // 终点
      val p = points[points.size - 1]
      val latLng = LatLng(p.lat, p.lng)
      val markerOptions = MarkerOptions()
        .position(latLng)
        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
      endMarkers.add(aMap!!.addMarker(markerOptions))
    }
    for (p in points) {
      val latLng = LatLng(p.lat, p.lng)
      polylineOptions.add(latLng)
      boundsBuilder.include(latLng)
    }
    val polyline = aMap?.addPolyline(polylineOptions)
    polyLines.add(polyline!!)
    aMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(boundsBuilder.build(), 30))
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
