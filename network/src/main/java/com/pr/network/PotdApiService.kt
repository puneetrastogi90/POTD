package com.pr.network

import com.pr.potd.network.data.PotdResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.*

interface PotdApiService {

    @GET("planetary/apod")
    suspend fun fetchNasaAPOD(@Query("date") date: String): Response<PotdResponse>
}