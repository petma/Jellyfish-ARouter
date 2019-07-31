package com.logic.jellyfish.utils

import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng

object Constants {

  /**
   * 服务id，请修改成您创建的服务的id
   *
   * 猎鹰轨迹服务，同一个开发者账号下的key可以直接使用同账号下的sid，不再需要人工绑定
   */
  const val SERVICE_ID: Long = 52085

  const val TRACK_SERVICE_KEY = "464bd5a65178ca64ee92df5031e83be4"

  const val PREF_NAME = "jellyfish_pref"

  const val PREF_KEY_TERMINAL_NAME = "terminal_name"

  const val PREF_KEY_TERMINAL_ID = "terminal_id"


  val LUJIAZUI = CameraPosition.Builder()
    .target(LatLng(31.238068, 121.501654)).zoom(18f).bearing(0f).tilt(0f).build()

  val SHEN_ZHEN = CameraPosition.Builder()
    .target(LatLng(22.547, 114.085947)).zoom(18f).bearing(0f).tilt(0f).build()

}
