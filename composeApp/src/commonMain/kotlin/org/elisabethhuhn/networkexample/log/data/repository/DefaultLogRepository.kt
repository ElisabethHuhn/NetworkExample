package org.elisabethhuhn.networkexample.log.data.repository


import org.elisabethhuhn.networkexample.core.domain.DataError
import org.elisabethhuhn.networkexample.core.domain.Result
import org.elisabethhuhn.networkexample.log.data.network.RemoteLogDataSource
import org.elisabethhuhn.networkexample.log.domain.LogRepository
import org.elisabethhuhn.networkexample.log.domain.LogEntry

class DefaultLogRepository(
    private val remoteLogDataSource: RemoteLogDataSource,
): LogRepository {
    override suspend fun sendLogBuffer(
        logItemsList: List<LogEntry>
    ): Result<String, DataError.Remote> {
        return remoteLogDataSource.logRemote(logItemsList)
    }
}