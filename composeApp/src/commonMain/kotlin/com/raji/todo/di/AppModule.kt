package com.raji.todo.di

import com.raji.todo.data.repositories.TaskRepositoryImpl
import com.raji.todo.data.repositories.UserRepositoryImpl
import com.raji.todo.domain.repository.TaskRepository
import com.raji.todo.domain.repository.UserRepository
import com.raji.todo.network.HttpClientFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module



expect val platformModule: Module
val sharedModule = module {


    single { HttpClientFactory.create(get(),get()) }
    singleOf(::TaskRepositoryImpl).bind<TaskRepository>()
    singleOf(::UserRepositoryImpl).bind<UserRepository>()
}
