package com.pr.potd.network

import com.pr.potd.network.data.ErrorResponse
import com.pr.potd.network.data.PotdResponse
import com.pr.potd.network.data.Result

interface NetworkDataSource {

    suspend fun fetchNasaPotd(date: String): Result<PotdResponse, String>
}