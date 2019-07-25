package com.logic.jellyfish.utils

import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng

object Constants {

   /**
    * 终端名称，该名称可以根据使用方业务而定，比如可以是用户名、用户手机号等唯一标识
    *
    * 通常应该使用该名称查询对应终端id，确定该终端是否存在，如果不存在则创建，然后就可以开启轨迹上报，将上报的轨迹关联
    * 到该终端
    */
   const val TERMINAL_NAME = "qq"

   /**
    * 服务id，请修改成您创建的服务的id
    *
    * 猎鹰轨迹服务，同一个开发者账号下的key可以直接使用同账号下的sid，不再需要人工绑定
    */
   const val SERVICE_ID: Long = 52085

   const val TRACK_SERVICE_KEY = "464bd5a65178ca64ee92df5031e83be4"


   val LUJIAZUI = CameraPosition.Builder()
      .target(LatLng(31.238068, 121.501654)).zoom(18f).bearing(0f).tilt(0f).build()

   val SHEN_ZHEN = CameraPosition.Builder()
      .target(LatLng(114.085947, 22.547)).zoom(18f).bearing(0f).tilt(0f).build()

}
