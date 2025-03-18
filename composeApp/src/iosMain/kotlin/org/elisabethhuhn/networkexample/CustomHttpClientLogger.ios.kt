package org.elisabethhuhn.networkexample

class IOSCustomHttpClientLogger:  io.ktor.client.plugins.logging.Logger {
    val logTag: String = "IOSCustomHttpClientLogger"
    override fun log(message: String) {
        TODO("Not yet implemented")
    }
}

actual fun getHttpClientLogger() = IOSCustomHttpClientLogger() as  io.ktor.client.plugins.logging.Logger
