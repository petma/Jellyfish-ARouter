package com.logic.jellyfish.utils

import com.amap.api.maps.model.LatLng

object MapUtils {

   fun getSmoothPath(originList: List<LatLng>): List<LatLng> {
      val pathSmoothTool = PathSmoothTool()
      pathSmoothTool.intensity = 4
      return pathSmoothTool.pathOptimize(originList)
   }
}