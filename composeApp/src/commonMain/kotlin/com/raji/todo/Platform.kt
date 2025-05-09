package com.raji.todo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform