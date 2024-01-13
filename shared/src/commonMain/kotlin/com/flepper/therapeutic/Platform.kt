package com.flepper.therapeutic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform