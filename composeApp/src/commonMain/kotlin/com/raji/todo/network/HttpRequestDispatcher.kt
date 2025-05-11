package com.raji.todo.network

import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.ensureActive
import kotlin.coroutines.coroutineContext

suspend inline fun <reified D> safeCall(request: () -> HttpResponse): Result<D, DataError> {

    val response = try {
        request()
    } catch (e: SocketTimeoutException) {
        return Result.NetworkError(DataError.REQUEST_TIME_OUT)
    } catch (e: UnresolvedAddressException) {
        return Result.NetworkError(DataError.NO_INTERNET)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        return Result.NetworkError(DataError.UNKNOWN)
    }
    return response.toResult()
}