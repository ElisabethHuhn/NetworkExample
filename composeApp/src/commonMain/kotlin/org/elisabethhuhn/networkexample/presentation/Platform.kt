package org.elisabethhuhn.networkexample.presentation

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform