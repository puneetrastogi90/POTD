package com.pr.potd.database.local

import com.pr.potd.database.dataobjects.entities.PotdEntity

interface LocalDataSource {

    fun insertPictureOfTheDay(potdEntity: PotdEntity)

    fun getPictureOfTheDay(date: Long): PotdEntity?

    fun updatePictureOftheDay(potdEntity: PotdEntity): Int

    fun getFavorites(): List<PotdEntity>?

    fun clearTable()
}