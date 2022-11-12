package com.pr.potd.dataobjects

import androidx.room.*
import com.pr.potd.dataobjects.entities.PotdEntity

@Dao
interface PictureOfTheDayDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPicture(potd: PotdEntity)

    @Query("Select * from pictures where date=:date")
    fun getPictureOfTheDay(date: String): List<PotdEntity>

    @Query("Select * from pictures where isFavorite=1")
    fun getFavorites(): List<PotdEntity>

    @Update
    fun updatePicture(potd: PotdEntity) : Int



    @Query("DELETE FROM pictures")
    fun nukeTable()

}