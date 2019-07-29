package com.logic.jellyfish.data.room

import com.amap.api.maps.model.LatLng
import com.logic.jellyfish.data.entity.LocalLatLng
import com.logic.jellyfish.utils.ext.log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultRepository(private val dao: Dao) : Repository {

   override suspend fun getLatLngs(): List<LatLng> {
      return withContext(Dispatchers.IO) {
         val localLatLngs = dao.getLatLngs()
         val latlngs = arrayListOf<LatLng>()
         for (localLatLng in localLatLngs) {
            latlngs.add(LatLng(localLatLng.latitude, localLatLng.longitude))
         }
         latlngs
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
         //         log("定位数据: 纬度: $latitude, 经度: $longitude")
         dao.insertLatLng(LocalLatLng(latitude, longitude))
      }
   }
}