package com.pr.localdb.entities

sealed class DatabaseResult<out T : Any, out U : Any> {

    data class Success<out T : Any>(val body: T?) : DatabaseResult<T, Nothing>()

    data class DataBaseError<out U : Any>(val errorBody: U?) : DatabaseResult<Nothing, U>()

}