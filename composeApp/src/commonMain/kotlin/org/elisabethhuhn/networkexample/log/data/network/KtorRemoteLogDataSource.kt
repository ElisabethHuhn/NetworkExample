package org.elisabethhuhn.networkexample.log.data.network


import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import org.elisabethhuhn.networkexample.core.data.safeCall
import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.logger.domain.LogEntry

import org.elisabethhuhn.networkexample.core.domain.Result

private const val BASE_URL = "http://localhost:5000/log"
//private const val BASE_URL = "https://ehuhn.free.beeceptor.com/log"

class KtorRemoteLogDataSource(
    private val httpClient: HttpClient
): RemoteLogDataSource {

    override suspend fun logRemote(
        logItemsList: List<LogEntry>
    ): Result<String, DataError.Remote> {
        return safeCall<String> {
            httpClient.post(
                urlString =  BASE_URL
            ) {
                contentType(ContentType.Application.Json)
                setBody(logItemsList)
            }
        }
    }
}