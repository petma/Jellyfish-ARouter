package com.logic.jellyfish.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "LocalLatLng")
data class LocalLatLng(
  @ColumnInfo(name = "latitude") var latitude: Double,
  @ColumnInfo(name = "longitude") var longitude: Double,
  @PrimaryKey @ColumnInfo(name = "entryid") var id: String = UUID.randomUUID().toString()
)