package org.elisabethhuhn.networkexample.logger

import org.elisabethhuhn.networkexample.model.loggermodel.LogEntry
import org.elisabethhuhn.networkexample.model.loggermodel.LogEntryBuffer
import org.elisabethhuhn.networkexample.networking.LoggingClient
import org.elisabethhuhn.networkexample.util.formatCurrentTimestamp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.elisabethhuhn.networkexample.util.onError
import org.elisabethhuhn.networkexample.util.onSuccess

class Logger (loggingClient: LoggingClient){
    private val _loggerState = MutableStateFlow<LoggerState>(LoggerState())
    val loggerState = _loggerState.asStateFlow()

    //    private val platform = getPlatform()
    private val logEntryBuffer = LogEntryBuffer()

    //lock for concurrent access to the buffer
    private val bufferLock = Mutex()

    fun setBufferLength(length: Int) {
        _loggerState.update { it.copy(bufferLength = length) }
    }
    fun setBufferDuration(duration: Long) {
        _loggerState.update { it.copy(bufferDuration = duration) }
    }

    private var client = loggingClient

    suspend fun log(msg: String, severity : String)  {
        val currentTimestamp = formatCurrentTimestamp()

        val currentLogEntry = LogEntry(
            message = msg,
            timestamp = currentTimestamp,
            severity = severity)
        logEntryBuffer.entries.add(currentLogEntry)

        if ((logEntryBuffer.entries.isNotEmpty()) &&
            (logEntryBuffer.entries.size >= loggerState.value.bufferLength)
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

        _loggerState.update {
            it.copy(lastBufferMessage = message)
        }

        client.logRemote(logEntries)
            .onSuccess { resultString ->
                _loggerState.update {
                    it.copy(requestResult = resultString)
                }
            }
            .onError { errorMessage ->
                _loggerState.update {
                    it.copy(errorResult = errorMessage.toString())
                }
            }
    }
}