package org.elisabethhuhn.networkexample.log.domain

interface CustomHttpClientLogger : io.ktor.client.plugins.logging.Logger{
    val logTag: String
    override fun log(message: String)
}

expect fun getHttpClientLogger():  io.ktor.client.plugins.logging.Logger
