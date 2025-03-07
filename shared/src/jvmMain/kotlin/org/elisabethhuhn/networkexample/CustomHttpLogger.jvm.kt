package org.elisabethhuhn.networkexample

class JVMCustomHttpLogger:  io.ktor.client.plugins.logging.Logger {
    val logTag: String = "JVMCustomHttpLogger"
    override fun log(message: String) {
        TODO("Not yet implemented")
    }
}

actual fun getHttpLogger() = JVMCustomHttpLogger() as  io.ktor.client.plugins.logging.Logger