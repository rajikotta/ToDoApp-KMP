package com.raji.todo.data.repositories

import androidx.datastore.core.DataStore
import com.raji.todo.data.datasource.UserRemoteDataSource
import com.raji.todo.data.models.TokenPairDto
import com.raji.todo.domain.repository.UserRepository
import com.raji.todo.network.map

class UserRepositoryImpl(
    val dataStore: DataStore<TokenPairDto>,
    val userRemoteDataSource: UserRemoteDataSource
) : UserRepository {

    override suspend fun login(username: String, password: String) {

        userRemoteDataSource.login(username, password).map { tokenPair ->
            dataStore.updateData { currentData ->
                currentData.copy(
                    accessToken = tokenPair.accessToken,
                    refreshToken = tokenPair.refreshToken
                )
            }
        }
    }

    override suspend fun register(name: String, username: String, password: String) {
        userRemoteDataSource.signup(name, username, password)
    }
}