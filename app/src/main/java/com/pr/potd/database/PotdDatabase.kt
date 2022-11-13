package com.pr.potd.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pr.potd.database.dataobjects.PictureOfTheDayDao
import com.pr.potd.database.dataobjects.entities.PotdEntity
import com.pr.potd.database.utils.DateTypeConverter

@Database(entities = [PotdEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class PotdDatabase : RoomDatabase() {

    abstract fun getPicturOftheDayDao(): PictureOfTheDayDao
}