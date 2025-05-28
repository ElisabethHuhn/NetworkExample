package org.elisabethhuhn.networkexample.log.data.network


import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.elisabethhuhn.networkexample.BASE_URL
import org.elisabethhuhn.networkexample.SERVER_PORT
import org.elisabethhuhn.networkexample.core.data.safeCall
import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.log.domain.LogEntry

import org.elisabethhuhn.networkexample.core.domain.Result

//private const val BASE_URL = "http://localhost:5000/log"
//private const val BASE_URL = "https://ehuhn.free.beeceptor.com/log"

class KtorRemoteLogDataSource(
    private val httpClient: HttpClient
): RemoteLogDataSource {

    override suspend fun logRemote(
        logItemsList: List<LogEntry>
    ): Result<String, DataError.Remote> {
        val url = "$BASE_URL:$SERVER_PORT/log"
        return safeCall<String> {
            httpClient.post(
                urlString =  url
            ) {
                contentType(ContentType.Application.Json)
                setBody(logItemsList)
            }
        }
    }
}