package com.logic.jellyfish.data.room

import com.amap.api.maps.model.LatLng

/**
 * 这里是使用数据库的操作
 */
interface Repository {

  suspend fun insertPathRecord(
    latitude: Double,
    longitude: Double,
    speed: Float,
    bearing: Float,
    time: Long
  )

  suspend fun deleteLatLngs()

  suspend fun getOptimizedLatLngs(): List<LatLng>

  suspend fun getPathRecords(): List<PathRecord>

}