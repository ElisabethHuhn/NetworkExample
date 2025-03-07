package org.elisabethhuhn.networkexample.model.loggermodel

import kotlinx.serialization.Serializable

@Serializable
data class LogEntry(
    val message: String,
    val timestamp: String,
    val severity: String
)
