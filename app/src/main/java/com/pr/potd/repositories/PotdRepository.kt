package com.pr.potd.repositories

import com.pr.potd.dataobjects.entities.DatabaseResult
import com.pr.potd.dataobjects.entities.PotdEntity
import com.pr.potd.network.data.PotdResponse
import com.pr.potd.network.data.Result

interface PotdRepository {

    suspend fun getPOTD(date: String): Result<PotdEntity, String>

    suspend fun updatePotd(potdEntity: PotdEntity): DatabaseResult<PotdEntity, String>

    suspend fun fetchFavorites(): DatabaseResult<List<PotdEntity>, String>
}