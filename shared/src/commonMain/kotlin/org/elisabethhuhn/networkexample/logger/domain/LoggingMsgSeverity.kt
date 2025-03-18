package org.elisabethhuhn.networkexample.logger.domain

enum class LoggingMsgSeverity {
    DEBUG,
    INFO,
    WARN,
    ERROR,
    NOT_ASSIGNED;

    companion object {
        fun toLoggingMsgSeverity(severity: String) : LoggingMsgSeverity {
            return when (severity) {
                "INFO" -> INFO
                "DEBUG" -> DEBUG
                "WARNING" -> WARN
                "ERROR" -> ERROR
                else -> NOT_ASSIGNED
            }
        }

        fun toString(severity: LoggingMsgSeverity) : String {
            return when (severity) {
                INFO -> "INFO"
                DEBUG -> "DEBUG"
                WARN -> "WARNING"
                ERROR -> "ERROR"
                else -> "NOT_ASSIGNED"
            }
        }
    }
}

