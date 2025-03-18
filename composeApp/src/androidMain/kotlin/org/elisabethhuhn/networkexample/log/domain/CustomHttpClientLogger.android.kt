package org.elisabethhuhn.networkexample.log.domain

import android.util.Log

class AndroidCustomHttpClientLogger :
    CustomHttpClientLogger {
    override val logTag = "AndroidCustomHttpClientLogger"
    override fun log(message: String) {
       Log.i(logTag, message)
    }
}

actual fun getHttpClientLogger() = AndroidCustomHttpClientLogger() as  io.ktor.client.plugins.logging.Logger