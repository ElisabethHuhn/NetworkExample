package org.elisabethhuhn.networkexample.presentation

import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.logger.LoggerState
import org.elisabethhuhn.networkexample.model.loggermodel.LoggingMsgSeverity

data class MainState(
    val isLoading: Boolean = true,
    val logger: Logger? = null,
    val currentTimestamp: String = "NO TIMESTAMP YET",
    val logCounter: Int = 0,
    val numberToGenerate: Int = 1,
    val severity: LoggingMsgSeverity = LoggingMsgSeverity.NOT_ASSIGNED,
    val message: String = "NO MESSAGE YET",
    val loggerState: LoggerState = LoggerState()
)
