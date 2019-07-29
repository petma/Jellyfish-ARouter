package com.logic.jellyfish.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.logic.jellyfish.data.entity.LocalLatLng

@Database(entities = [LocalLatLng::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

   abstract fun dao(): Dao
}