package com.pr.localdb

import com.pr.localdb.entities.PotdEntity

interface LocalDataSource {

    fun insertPictureOfTheDay(potdEntity: PotdEntity)

    fun getPictureOfTheDay(date: Long): PotdEntity?

    fun updatePictureOftheDay(potdEntity: PotdEntity): Int

    fun getFavorites(): List<PotdEntity>?

    fun clearTable()
}