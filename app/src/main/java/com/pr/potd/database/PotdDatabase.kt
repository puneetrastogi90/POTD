package com.pr.potd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pr.potd.dataobjects.PictureOfTheDayDao
import com.pr.potd.dataobjects.entities.PotdEntity

@Database(entities = [PotdEntity::class], version = 1)
abstract class PotdDatabase : RoomDatabase() {

    abstract fun getPicturOftheDayDao(): PictureOfTheDayDao
}