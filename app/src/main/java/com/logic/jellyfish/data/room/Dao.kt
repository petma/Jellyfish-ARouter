package com.logic.jellyfish.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.logic.jellyfish.data.entity.LocalLatLng

/**
 * 这里面都是直接对数据库的操作
 */
@Dao
interface Dao {

   @Query("SELECT * FROM LocalLatLng")
   suspend fun getLatLngs(): List<LocalLatLng>

   @Query("DELETE FROM LocalLatLng")
   suspend fun deleteLatLngs()

   @Insert
   suspend fun insertLatLng(latLng: LocalLatLng)

}