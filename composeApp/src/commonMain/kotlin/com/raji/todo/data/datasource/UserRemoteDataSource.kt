package com.raji.todo.data.datasource

import com.raji.todo.data.models.TokenPairDto
import com.raji.todo.network.DataError
import com.raji.todo.network.Result
import com.raji.todo.network.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class UserRemoteDataSource(private val htpClient: HttpClient) {


    suspend fun login(
        username: String, password: String
    ): Result<TokenPairDto, DataError> {

        return safeCall {
            htpClient.post(urlString = "$BASE_URL/auth/login") {

                parameter("username", username)
                parameter("password", password)
            }
        }
    }

    suspend fun signup(
        name: String, username: String, password: String
    ): Result<TokenPairDto, DataError> {
        return safeCall {
            htpClient.post(urlString = "$BASE_URL/auth/register") {

                parameter("name", name)
                parameter("username", username)
                parameter("password", password)
            }
        }
    }

}

const val BASE_URL = "https://openlibrary.org"


