package org.elisabethhuhn.networkexample.log.domain

import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.core.domain.Result

interface LogRepository {
    suspend fun sendLogBuffer(logItemsList: List<LogEntry>): Result<String, DataError.Remote>
}