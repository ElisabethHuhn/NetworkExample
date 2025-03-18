package org.elisabethhuhn.networkexample.presentation

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.elisabethhuhn.networkexample.app.App
import org.elisabethhuhn.networkexample.di.initKoin

fun main() = application {
    initKoin()
    application {
        Window(
            onCloseRequest = ::exitApplication,
            title = "NetworkExample",
        ) {
            App()
        }
    }

}