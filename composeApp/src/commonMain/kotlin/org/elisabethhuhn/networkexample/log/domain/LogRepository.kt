package org.elisabethhuhn.networkexample.log.domain

import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.core.domain.Result
import org.elisabethhuhn.networkexample.logger.domain.LogEntry

interface LogRepository {
    suspend fun lofRemote( logItemsList: List<LogEntry>): Result<String, DataError.Remote>
}