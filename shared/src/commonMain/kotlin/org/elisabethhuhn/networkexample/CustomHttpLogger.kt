package org.elisabethhuhn.networkexample

import io.ktor.client.plugins.logging.Logger

interface CustomHttpLogger : io.ktor.client.plugins.logging.Logger{
    val logTag: String
    override fun log(message: String)
}

expect fun getHttpLogger():  io.ktor.client.plugins.logging.Logger
