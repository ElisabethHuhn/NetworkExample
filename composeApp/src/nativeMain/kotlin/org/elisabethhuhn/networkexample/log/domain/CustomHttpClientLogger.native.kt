package org.elisabethhuhn.networkexample.log.domain

class NativeCustomHttpClientLogger:  io.ktor.client.plugins.logging.Logger {
    val logTag: String = "NativeCustomHttpClientLogger"
    override fun log(message: String) {
        TODO("Not yet implemented")
    }
}

actual fun getHttpClientLogger() = NativeCustomHttpClientLogger() as  io.ktor.client.plugins.logging.Logger
