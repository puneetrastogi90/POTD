package com.pr.localdb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pr.localdb.dao.PictureOfTheDayDao
import com.pr.localdb.entities.PotdEntity
import com.pr.potd.database.utils.DateTypeConverter

@Database(entities = [PotdEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class PotdDatabase : RoomDatabase() {

    abstract fun getPicturOftheDayDao(): PictureOfTheDayDao
}