package com.raji.todo.domain.repository

interface UserRepository {

    suspend fun login(username: String, password: String): Unit

    suspend fun register(name:String,username: String, password: String): Unit
}