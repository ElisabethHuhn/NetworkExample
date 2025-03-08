package org.elisabethhuhn.networkexample.presentation

import org.elisabethhuhn.networkexample.logger.Logger
import org.elisabethhuhn.networkexample.logger.LoggerState

sealed interface MainAction {
    data object OnBackClick: MainAction
    data class NumberToGenerateChanged(val number: String): MainAction
    data class UpdateLogger(val logger: Logger): MainAction
    data object IncrLogCounter: MainAction
    data object SetCurrentTimestamp: MainAction
    data class ReportLoggingResult(val loggerState: LoggerState): MainAction
    data class UpdateLoggerBufferLength(val lengthString: String): MainAction
    data class UpdateLoggerBufferDuration(val durationString: String): MainAction
}