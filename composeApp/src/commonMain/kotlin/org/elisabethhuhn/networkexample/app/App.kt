package org.elisabethhuhn.networkexample.app

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.presentation.MainScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(logger: Logger) {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        MainScreen(logger)
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(
//                onClick = {
//                    showContent = !showContent
//                }) {
//                Text("Click me!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(
//                    Modifier.fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}