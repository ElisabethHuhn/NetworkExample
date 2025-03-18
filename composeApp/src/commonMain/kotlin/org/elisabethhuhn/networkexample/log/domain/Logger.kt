package org.elisabethhuhn.networkexample.log.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.elisabethhuhn.networkexample.logger.domain.LogEntry
import org.elisabethhuhn.networkexample.logger.domain.LogEntryBuffer
import org.elisabethhuhn.networkexample.log.presentation.LogClientState
import org.elisabethhuhn.networkexample.log.data.network.LoggingClient
import org.elisabethhuhn.networkexample.logger.domain.LoggingMsgSeverity
import org.elisabethhuhn.networkexample.util.formatCurrentTimestamp
import org.elisabethhuhn.networkexample.util.onError
import org.elisabethhuhn.networkexample.util.onSuccess

class Logger (loggingClient: LoggingClient){
    //TODO this needs to be thread safe . For now just a KLUGE
    lateinit var reportResult : (LogClientState) -> Unit
    fun setReportResult(reportResult: (LogClientState) -> Unit) {
        this.reportResult = reportResult
    }

    private val _logClientState = MutableStateFlow(LogClientState())
//    val loggerState = _loggerState.asStateFlow()

    //    private val platform = getPlatform()
    private val logEntryBuffer = LogEntryBuffer()

    //lock for concurrent access to the buffer
    private val bufferLock = Mutex()

    fun setBufferLength(length: Int) {
        _logClientState.update { it.copy(bufferLength = length) }
        reportResult(_logClientState.value)
    }
    fun setBufferDuration(duration: Long) {
        _logClientState.update { it.copy(bufferDuration = duration) }
        reportResult(_logClientState.value)
    }

    private var client = loggingClient

    suspend fun log(msg: String, severity : String)  {
        val currentTimestamp = formatCurrentTimestamp()

        // for some reason valueOf doesn't work, so try BRUTE force
//            msgSeverity = LoggingMsgSeverity.valueOf(severity)
        val msgSeverity =  LoggingMsgSeverity.toLoggingMsgSeverity (severity)

        val currentLogEntry = LogEntry(
            message = msg,
            timestamp = currentTimestamp,
            severity = msgSeverity
        )
        logEntryBuffer.entries.add(currentLogEntry)

        if ((logEntryBuffer.entries.isNotEmpty()) &&
            (logEntryBuffer.entries.size >= _logClientState.value.bufferLength)
        ) {
            flushBuffer()
        }
    }

    suspend fun flushBuffer() {
        if (logEntryBuffer.entries.isNotEmpty()) {
            //make a copy of the list for the actual send call
            var shallowListCopy : List<LogEntry>
            bufferLock.withLock {
                shallowListCopy = buildList { addAll(logEntryBuffer.entries) }
                logEntryBuffer.entries.clear()
            }
            sendBuffer(logEntries = shallowListCopy)
        }
    }

    private  suspend fun sendBuffer(logEntries: List<LogEntry> ) {
        val lastEntry = logEntries.last()
        val message = "Last buffer sent at ${lastEntry.timestamp} \nwith ${logEntries.size} entries "

        _logClientState.update {
            it.copy(lastBufferMessage = message)
        }

        client.logRemote(logEntries)
            .onSuccess { resultString ->
                _logClientState.update {
                    it.copy(requestResult = resultString)
                }
                reportResult(_logClientState.value)
            }
            .onError { errorMessage ->
                _logClientState.update {
                    it.copy(errorResult = errorMessage.toString())
                }
                reportResult(_logClientState.value)
            }
    }
}