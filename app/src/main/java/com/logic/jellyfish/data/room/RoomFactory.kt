package com.logic.jellyfish.data.room

import androidx.room.Room
import com.logic.jellyfish.App

object RoomFactory {

  val repository: Repository by lazy { getDefaultRepository() }

  private fun getDefaultRepository(): Repository {
    return DefaultRepository(
      Room.databaseBuilder(
        App.app,
        Database::class.java,
        "Location.db"
      ).build().dao()
    )

  }
}