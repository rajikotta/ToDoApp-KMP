package com.raji.todo

import android.app.Application
import com.raji.todo.di.initKoin
import org.koin.android.ext.koin.androidContext

class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@TaskApplication)
        }
    }
}