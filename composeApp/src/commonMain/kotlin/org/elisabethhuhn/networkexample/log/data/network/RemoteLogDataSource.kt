package org.elisabethhuhn.networkexample.log.data.network

import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.logger.domain.LogEntry
import org.elisabethhuhn.networkexample.core.domain.Result

interface RemoteLogDataSource {
    suspend fun logRemote(
        logItemsList: List<LogEntry>
    ): Result<String, DataError.Remote>
}