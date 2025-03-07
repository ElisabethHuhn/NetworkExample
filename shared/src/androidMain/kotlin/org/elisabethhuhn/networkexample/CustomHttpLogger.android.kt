package org.elisabethhuhn.networkexample

import android.os.Build
import android.util.Log
import java.util.logging.Logger

class AndroidCustomHttpLogger : CustomHttpLogger {
    override val logTag = "AndroidCustomHttpLogger"
    override fun log(message: String) {
       Log.i(logTag, message)
    }
}

actual fun getHttpLogger() = AndroidCustomHttpLogger() as  io.ktor.client.plugins.logging.Logger