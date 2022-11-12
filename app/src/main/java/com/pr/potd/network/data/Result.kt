package com.pr.potd.network.data

import com.pr.potd.exceptions.NoInternetException

sealed class Result<out T : Any, out U : Any> {

    data class Success<out T : Any>(val body: T?) : Result<T, Nothing>()

    data class ApiError<out U : Any>(val errorBody: U?) : Result<Nothing, U>()

    data class NetworkError(val error: NoInternetException) : Result<Nothing, Nothing>()

    data class UnknownError(val error: Throwable?) : Result<Nothing, Nothing>()
}



