package com.pr.potd.local

import com.pr.potd.dataobjects.entities.PotdEntity

interface LocalDataSource {

    fun insertPictureOfTheDay(potdEntity: PotdEntity)

    fun getPictureOfTheDay(date: String): PotdEntity?

    fun updatePictureOftheDay(potdEntity: PotdEntity): Int

    fun getFavorites(): List<PotdEntity>?

    fun clearTable()
}