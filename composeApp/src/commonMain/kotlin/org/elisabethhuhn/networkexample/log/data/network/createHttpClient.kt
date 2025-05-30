package org.elisabethhuhn.networkexample.log.data.network



import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.elisabethhuhn.networkexample.log.domain.getHttpClientLogger


fun createHttpClient(engine: HttpClientEngine): HttpClient {
    return HttpClient(engine) {
        //configure the engine by installing features
        //Ktor logging of network requests
        install(Logging) {
            logger = getHttpClientLogger()
            level = LogLevel.ALL
        }
        //Json parsing
        install(ContentNegotiation) {
            json(
                json = Json {
                    //ignore fields we aren't using
                    ignoreUnknownKeys = true
                }
            )
        }
        //can install authentication here
    }
}

