package org.elisabethhuhn.networkexample

import android.os.Build
import org.elisabethhuhn.networkexample.presentation.Platform

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()