package org.elisabethhuhn.networkexample.log.mappers


import org.elisabethhuhn.networkexample.log.data.LogEntryDto
import org.elisabethhuhn.networkexample.log.domain.LogEntry
import org.elisabethhuhn.networkexample.log.domain.LoggingMsgSeverity

fun LogEntryDto.toLogEntry() = LogEntry(
    message = message ?: "",
    timestamp = timestamp ?: "",
    severity = LoggingMsgSeverity.valueOf(severity ?: "NOT_ASSIGNED")
)