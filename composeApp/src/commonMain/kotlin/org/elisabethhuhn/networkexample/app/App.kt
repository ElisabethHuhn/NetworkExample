package org.elisabethhuhn.networkexample.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.elisabethhuhn.networkexample.log.domain.Logger
import org.elisabethhuhn.networkexample.log.presentation.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(logger: Logger) {
    MaterialTheme {

        MainScreen(logger)
    }
}

