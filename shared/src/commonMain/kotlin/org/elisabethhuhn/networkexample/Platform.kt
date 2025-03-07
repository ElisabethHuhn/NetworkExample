package org.elisabethhuhn.networkexample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform