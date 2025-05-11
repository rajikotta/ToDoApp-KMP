package com.raji.todo.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse


enum class DataError {
    REQUEST_TIME_OUT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    SERVER,
    SERIALIZATION,
    UNKNOWN
}


sealed interface Result<out T, out E : DataError> {
    data class Success<T>(val data: T) : Result<T, Nothing>
    data class NetworkError<E : DataError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : DataError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }

        else -> this
    }
}

inline fun <T, E : DataError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.NetworkError -> {
            action(error)
            this
        }

        else -> this
    }
}


inline fun <D, E : DataError, R> Result<D, E>.map(map: (D) -> R): Result<R, E> {
    return when (this) {
        is Result.Success ->
            Result.Success(map(this.data))

        is Result.NetworkError -> TODO()
    }
}

suspend inline fun <reified D> HttpResponse.toResult(): Result<D, DataError> {

    return when (status.value) {
        in 200..299 ->
            try {
                Result.Success(body<D>())
            } catch (e: Exception) {
                Result.NetworkError(DataError.SERIALIZATION)
            }


        408 -> Result.NetworkError(DataError.REQUEST_TIME_OUT)
        429 -> Result.NetworkError(DataError.TOO_MANY_REQUESTS)
        in 500..599 -> Result.NetworkError(DataError.SERVER)
        else -> Result.NetworkError(DataError.UNKNOWN)
    }
}