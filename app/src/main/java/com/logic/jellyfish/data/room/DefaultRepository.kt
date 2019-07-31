package com.logic.jellyfish.data.room

import com.amap.api.maps.model.LatLng
import com.logic.jellyfish.data.entity.LocalLatLng
import com.logic.jellyfish.utils.PathSmoothTool
import com.logic.jellyfish.utils.ext.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(private val dao: Dao) : Repository {

  override suspend fun getOptimizedLatLngs(): List<LatLng> {
    return withContext(Dispatchers.IO) {
      val localLatLngs = dao.getLatLngs()

      if (localLatLngs.isNotEmpty()) {
        var latLngs = arrayListOf<LatLng>()
        for (localLatLng in localLatLngs) {
          latLngs.add(LatLng(localLatLng.latitude, localLatLng.longitude))
        }
        val pathSmoothTool = PathSmoothTool()
        pathSmoothTool.intensity = 4

        val a = StringBuilder()
        a.append("本地LocalLatLng")
        for (localLatLng in localLatLngs) {
          a.append("纬度: ${localLatLng.latitude}, 经度: ${localLatLng.longitude}\n")
        }
        log(a.toString())

        val b = StringBuilder()
        b.append("高德LatLng")
        for (latLng in latLngs) {
          b.append("纬度: ${latLng.latitude}, 经度: ${latLng.longitude}\n")
        }
        log(b.toString())

        latLngs = pathSmoothTool.pathOptimize(latLngs) as ArrayList<LatLng>
        latLngs
      } else {
        emptyList<LatLng>()
      }
    }
  }

  override suspend fun deleteLatLngs() {
    withContext(Dispatchers.IO) {
      log("删除成功")
      dao.deleteLatLngs()
    }
  }

  override suspend fun insertLatLng(latitude: Double, longitude: Double) {
    withContext(Dispatchers.IO) {
      log("定位数据: 纬度: $latitude, 经度: $longitude")
      dao.insertLatLng(LocalLatLng(latitude, longitude))
    }
  }
}