package com.pr.localdb.dao

import androidx.room.*
import com.pr.localdb.entities.PotdEntity

@Dao
interface PictureOfTheDayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPicture(potd: PotdEntity)

    @Query("Select * from pictures where date=:date")
    fun getPictureOfTheDay(date: Long): List<PotdEntity>

    @Query("Select * from pictures where isFavorite=1")
    fun getFavorites(): List<PotdEntity>

    @Update
    fun updatePicture(potd: PotdEntity) : Int



    @Query("DELETE FROM pictures")
    fun nukeTable()

}