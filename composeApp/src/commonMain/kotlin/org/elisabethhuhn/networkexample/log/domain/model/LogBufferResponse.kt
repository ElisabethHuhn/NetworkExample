package org.elisabethhuhn.networkexample.log.domain.model

import org.elisabethhuhn.networkexample.log.domain.LogEntry
import org.elisabethhuhn.networkexample.log.domain.LoggingMsgSeverity

data class LogBufferResponse(
    val bufferSize : Int = 0,
    var totalSent : Int = 0,
    var numberQueued : Int = 0,
    var numberSuccess: Int = 0,
    var numberError : Int = 0,
    var lastQueued : LogEntry = LogEntry(
        message = "NO MESSAGE YET",
        timestamp = "NO TIMESTAMP YET",
        severity = LoggingMsgSeverity.NOT_ASSIGNED
    ),
    var lastSent : LogEntry = LogEntry(
        message = "NO MESSAGE YET",
        timestamp = "NO TIMESTAMP YET",
        severity = LoggingMsgSeverity.NOT_ASSIGNED
    ),

    )
