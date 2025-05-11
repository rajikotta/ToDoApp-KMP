package com.raji.todo.network

import androidx.datastore.core.DataStore
import com.raji.todo.data.models.TokenPairDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.forms.submitForm
import io.ktor.http.ContentType
import io.ktor.http.Parameters
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.collectLatest
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(engine: HttpClientEngine, dataStore: DataStore<TokenPairDto>): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(json = Json {
                    ignoreUnknownKeys = true
                })
            }
            install(HttpTimeout) {
                socketTimeoutMillis = 20_000L
                requestTimeoutMillis = 20_000L

            }
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
                level = LogLevel.ALL
            }

            val client = HttpClient(CIO) {
                install(Auth) {
                    bearer {
                        loadTokens {
                            var tokenPairDto: TokenPairDto? = null
                            dataStore.data.collectLatest {
                                tokenPairDto = it
                            }
                            BearerTokens(
                                accessToken = tokenPairDto?.accessToken ?: "",
                                refreshToken = tokenPairDto?.refreshToken ?: ""
                            )

                        }

                        refreshTokens {
                            try {
                                var currentRefreshToken = ""
                                dataStore.data.collectLatest {
                                    currentRefreshToken = it.refreshToken
                                }
                                val tokenPairDto: TokenPairDto = client.submitForm(
                                    url = "https://your.api/refresh",
                                    formParameters = Parameters.build {
                                        append("refreshToken", currentRefreshToken)
                                    }
                                ).body()

                                return@refreshTokens BearerTokens(
                                    accessToken = tokenPairDto.accessToken,
                                    refreshToken = tokenPairDto.refreshToken
                                )
                            } catch (e: Exception) {
                                println("Token refresh failed: ${e.message}")
                                return@refreshTokens null
                            }
                        }


                    }
                }


                defaultRequest {
                    contentType(ContentType.Application.Json)
                }
            }
        }
    }
}

