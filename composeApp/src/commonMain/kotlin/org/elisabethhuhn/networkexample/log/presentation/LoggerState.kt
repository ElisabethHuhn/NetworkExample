package org.elisabethhuhn.networkexample.log.presentation

import org.elisabethhuhn.networkexample.log.domain.Logger
import org.elisabethhuhn.networkexample.logger.domain.LoggingMsgSeverity

data class LoggerState(
    val isLoading: Boolean = true,
    val logger: Logger? = null,
    val currentTimestamp: String = "NO TIMESTAMP YET",
    val logCounter: Int = 0,
    val numberToGenerate: Int = 1,
    val severity: LoggingMsgSeverity = LoggingMsgSeverity.NOT_ASSIGNED,
    val message: String = "NO MESSAGE YET",
    val logClientState: LogClientState = LogClientState()
)
