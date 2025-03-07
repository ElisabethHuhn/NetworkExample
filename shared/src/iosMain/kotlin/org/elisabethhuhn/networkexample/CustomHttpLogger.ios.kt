package org.elisabethhuhn.networkexample

class IOSCustomHttpLogger:  io.ktor.client.plugins.logging.Logger {
    val logTag: String = "IOSCustomHttpLogger"
    override fun log(message: String) {
        TODO("Not yet implemented")
    }
}

actual fun getHttpLogger() = IOSCustomHttpLogger() as  io.ktor.client.plugins.logging.Logger
