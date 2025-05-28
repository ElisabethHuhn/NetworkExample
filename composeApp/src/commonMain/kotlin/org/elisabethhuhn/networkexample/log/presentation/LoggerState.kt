package org.elisabethhuhn.networkexample.log.presentation

import org.elisabethhuhn.networkexample.log.domain.LogEntry
import org.elisabethhuhn.networkexample.log.domain.LoggingMsgSeverity

data class LoggerState(
    val isLoading: Boolean = true,

    val bufferLength: Int = 1,
    val bufferDuration: Long = 1000L, // 1 second
    val numberToGenerate: Int = 1,

    val lastBuffered : LogEntry = LogEntry(
        message = "NO MESSAGE YET",
        timestamp = "NO TIMESTAMP YET",
        severity = LoggingMsgSeverity.NOT_ASSIGNED
    ),
    val lastSent : LogEntry = LogEntry(
        message = "NO MESSAGE YET",
        timestamp = "NO TIMESTAMP YET",
        severity = LoggingMsgSeverity.NOT_ASSIGNED
    ),

    val totalSent : Int = 0,
    val totalQueued : Int = 0,
    val totalSuccess: Int = 0,
    val totalError : Int = 0

)
