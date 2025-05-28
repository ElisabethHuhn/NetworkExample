package org.elisabethhuhn.networkexample.log.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogEntryDtoList (
    @SerialName("null") val entries: MutableList<LogEntryDto> = mutableListOf()
)

@Serializable
data class LogEntryDto(
    val message: String?,
    val severity: String?,
    val timestamp: String?
)
