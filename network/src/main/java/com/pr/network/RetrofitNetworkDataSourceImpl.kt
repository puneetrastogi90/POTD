package com.pr.network

import android.util.Log
import com.pr.potd.exceptions.NoInternetException
import com.pr.potd.network.data.ErrorResponse
import com.pr.potd.network.data.PotdResponse
import retrofit2.Response
import java.io.IOException
import com.pr.potd.network.data.Result

class RetrofitNetworkDataSourceImpl(private val apiService: PotdApiService) :
    NetworkDataSource {
    companion object {
        val TAG = "NetworkDataSourceImpl"
    }

    override suspend fun fetchNasaPotd(date: String): Result<PotdResponse, String> {
        var response: Response<PotdResponse>? = null
        try {
            response = apiService.fetchNasaAPOD(date)
            val body = response.body()
            val code = response.code()
            if (response.isSuccessful) {
                return Result.Success(body)
            } else {
                val errorResponse = response.errorBody()
                errorResponse?.string()?.let { Log.e(TAG, it) }
                return Result.ApiError("Something Went Wrong. please try again later.")
            }
        } catch (noInternetException: NoInternetException) {
            return Result.NetworkError(noInternetException)
        }
        return Result.UnknownError(IOException("Error while Fetching news"))
    }

}