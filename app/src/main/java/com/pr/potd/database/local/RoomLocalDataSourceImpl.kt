package com.pr.potd.database.local

import com.pr.potd.database.dataobjects.PictureOfTheDayDao
import com.pr.potd.database.dataobjects.entities.PotdEntity
import javax.inject.Inject

class RoomLocalDataSourceImpl @Inject constructor(private val pictureOfTheDayDao: PictureOfTheDayDao) :
    LocalDataSource {

    override fun insertPictureOfTheDay(potdEntity: PotdEntity) {
        pictureOfTheDayDao.insertPicture(potdEntity)
    }

    override fun getPictureOfTheDay(date: Long): PotdEntity? {
        val picturesOfTheDay = pictureOfTheDayDao.getPictureOfTheDay(date)
        if (picturesOfTheDay.isNotEmpty())
            return picturesOfTheDay[0]
        return null
    }

    override fun updatePictureOftheDay(potdEntity: PotdEntity): Int {
        return pictureOfTheDayDao.updatePicture(potdEntity)
    }

    override fun getFavorites(): List<PotdEntity> {
        return pictureOfTheDayDao.getFavorites()
    }

    override fun clearTable() {
        pictureOfTheDayDao.nukeTable()
    }
}