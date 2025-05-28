package org.elisabethhuhn.networkexample.log.domain

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.elisabethhuhn.networkexample.core.domain.DataError
import  org.elisabethhuhn.networkexample.core.domain.Result
import org.elisabethhuhn.networkexample.core.domain.onError
import org.elisabethhuhn.networkexample.core.domain.onSuccess
import org.elisabethhuhn.networkexample.log.domain.model.LogBufferResponse
import org.elisabethhuhn.networkexample.util.formatCurrentTimestamp
import org.elisabethhuhn.networkexample.util.getEpochMillis

class LogBusinessLogic (
    private val logRepository: LogRepository
){
    var logCounter = 0
    var logEntries = mutableListOf<LogEntry>()
    var maxBufferSize = 10
    fun setMaxBufferSize(newSize: Int) {
        maxBufferSize = newSize
    }

    var bufferDuration = 1000L // 1 second
    fun setBufferDuration(newDuration: Long) {
        bufferDuration = newDuration
    }

    var timeLastSent : Long = 0L

    val sendLock = Mutex(false)


    /*
     * Generate a number of log messages
     */
    suspend fun generateLogMessages(
        numberToGenerate: Int,
    ) : Result<LogBufferResponse, DataError.Remote> {

        val returnResponse = LogBufferResponse()

        for (i in 1..numberToGenerate) {
            //build a log message
            val timestamp = formatCurrentTimestamp()

            val severity = when {
                logCounter % 2 == 0 -> LoggingMsgSeverity.INFO
                logCounter % 3 == 0 -> LoggingMsgSeverity.ERROR
                logCounter % 5 == 0 -> LoggingMsgSeverity.WARN
                else -> LoggingMsgSeverity.DEBUG
            }
            val message = "Log message $logCounter"

            val logEntry = LogEntry(
                message = message,
                timestamp = timestamp,
                severity = severity,
                )
            val response = logMessage(logEntry)
            response.onSuccess {
                val successes = returnResponse.numberSuccess++
                returnResponse.numberSuccess = successes
            }
            response.onError {
                val errors = returnResponse.numberError++
                returnResponse.numberError = errors
            }

            logCounter++
        }
        returnResponse.totalSent = logCounter
        returnResponse.numberQueued = numberToGenerate

        return Result.Success(returnResponse)
    }

    suspend fun logMessage(logEntry: LogEntry) : Result<String, DataError.Remote> {
        logEntries.add(logEntry)

        //assume all we will do is add the message to the buffer an set up the response
        var response : Result<String, DataError.Remote> = Result.Success("Log message queued to send")

        val timeNowMs = getEpochMillis()
        val timeDif = timeNowMs - timeLastSent

        if ((logEntries.size >= maxBufferSize) ||
            (timeDif > bufferDuration)
        ) {
           response = flushBuffer()
        }
        return response
    }

    suspend fun flushBuffer() : Result<String, DataError.Remote>  {
        var response : Result<String, DataError.Remote>

        //lock out any other coroutines
        sendLock.withLock {
            val sendList = logEntries.toList()
            logEntries = mutableListOf<LogEntry>()
           response = logRepository.sendLogBuffer(sendList)
        }
        return response
    }
}