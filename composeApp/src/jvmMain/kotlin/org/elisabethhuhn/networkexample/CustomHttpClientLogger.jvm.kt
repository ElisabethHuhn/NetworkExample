package org.elisabethhuhn.networkexample

class JVMCustomHttpClientLogger:  io.ktor.client.plugins.logging.Logger {
    val logTag: String = "JVMCustomHttpClientLogger"
    override fun log(message: String) {
        TODO("Not yet implemented")
    }
}

actual fun getHttpClientLogger() = JVMCustomHttpClientLogger() as  io.ktor.client.plugins.logging.Logger