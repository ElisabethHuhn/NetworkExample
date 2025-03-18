package org.elisabethhuhn.networkexample.logger.mappers

import org.elisabethhuhn.networkexample.logger.data.LoggerDto
import org.elisabethhuhn.networkexample.logger.domain.LogEntry
import org.elisabethhuhn.networkexample.logger.domain.LoggingMsgSeverity

fun LoggerDto.toLogEntry() = LogEntry(
    message = message,
    timestamp = timestamp,
    severity = LoggingMsgSeverity.valueOf(severity)
)