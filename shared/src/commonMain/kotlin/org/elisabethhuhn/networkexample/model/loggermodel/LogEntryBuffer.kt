package org.elisabethhuhn.networkexample.model.loggermodel

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogEntryBuffer(
    @SerialName("null") val entries: MutableList<LogEntry> = mutableListOf()
)
