package org.elisabethhuhn.networkexample.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import io.ktor.client.engine.okhttp.OkHttp
import org.elisabethhuhn.networkexample.app.App
import org.elisabethhuhn.networkexample.log.domain.Logger
import org.elisabethhuhn.networkexample.log.data.network.LoggingClient
import org.elisabethhuhn.networkexample.log.data.network.createHttpClient


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
//            App()
            val engine = OkHttp.create()
            val client = createHttpClient(engine = engine)
            val loggingClient = LoggingClient(httpClient = client)
            val logger = Logger(loggingClient = loggingClient)

            App(
                logger = remember { logger }
            )

        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}