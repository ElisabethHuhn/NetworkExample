package org.elisabethhuhn.networkexample.log.domain

import io.ktor.client.plugins.logging.Logger


class DesktopCustomHttpClientLogger :
    CustomHttpClientLogger {
    override val logTag = "DesktopCustomHttpClientLogger"
    override fun log(message: String) {
//        Log.i(logTag, message)
    }
}

actual fun getHttpClientLogger(): Logger = DesktopCustomHttpClientLogger() as io.ktor.client.plugins.logging.Logger