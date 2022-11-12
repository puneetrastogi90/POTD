package com.pr.potd.repositories

import com.pr.potd.dataobjects.entities.DatabaseResult
import com.pr.potd.dataobjects.entities.PotdEntity
import com.pr.potd.local.LocalDataSource
import com.pr.potd.network.NetworkDataSource
import com.pr.potd.network.data.Result
import com.pr.potd.utils.toPotdEntity
import javax.inject.Inject

class PotdRepositoryImpl @Inject constructor(
    val networkDataSource: NetworkDataSource,
    val localDataSource: LocalDataSource
) :
    PotdRepository {

    override suspend fun getPOTD(date: String): Result<PotdEntity, String> {
        val result = networkDataSource.fetchNasaPotd(date)
        when (result) {
            is Result.Success -> {
                result.body?.toPotdEntity()?.let { localDataSource.insertPictureOfTheDay(it) }
                return Result.Success(localDataSource.getPictureOfTheDay(result.body!!.date))
            }

            is Result.NetworkError -> {
                val res = localDataSource.getPictureOfTheDay(date)
                if (res == null) {
                    return result
                } else {
                    return Result.Success(res)
                }
            }

            is Result.ApiError -> {
                val res = localDataSource.getPictureOfTheDay(date)
                if (res == null) {
                    return result
                } else {
                    return Result.Success(res)
                }
            }

            is Result.UnknownError -> {
                val res = localDataSource.getPictureOfTheDay(date)
                if (res == null) {
                    return result
                } else {
                    return Result.Success(res)
                }
            }
        }
    }

    override suspend fun updatePotd(potdEntity: PotdEntity): DatabaseResult<PotdEntity, String> {
        potdEntity.isFavorite = potdEntity.isFavorite.not()
        val res = localDataSource.updatePictureOftheDay(potdEntity)
        if (res > 0) {
            return DatabaseResult.Success(localDataSource.getPictureOfTheDay(potdEntity.date))
        } else {
            return DatabaseResult.DataBaseError("Something went wrong while trying to toggle favorite")
        }
    }

    override suspend fun fetchFavorites(): DatabaseResult<List<PotdEntity>, String> {
        return DatabaseResult.Success(localDataSource.getFavorites())
    }

}