package org.elisabethhuhn.networkexample.log.presentation

import org.elisabethhuhn.networkexample.log.domain.Logger

sealed interface LoggerAction {
    data object OnBackClick: LoggerAction
    data class NumberToGenerateChanged(val number: String): LoggerAction
    data class UpdateLogger(val logger: Logger): LoggerAction
    data object IncrLogCounter: LoggerAction
    data object SetCurrentTimestamp: LoggerAction
    data class ReportLoggingResult(val logClientState: LogClientState): LoggerAction
    data class UpdateLoggerBufferLength(val lengthString: String): LoggerAction
    data class UpdateLoggerBufferDuration(val durationString: String): LoggerAction
}