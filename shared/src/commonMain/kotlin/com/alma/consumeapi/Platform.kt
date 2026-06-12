package com.alma.consumeapi

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform