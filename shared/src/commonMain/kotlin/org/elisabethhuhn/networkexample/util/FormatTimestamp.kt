package org.elisabethhuhn.networkexample.util

import kotlinx.datetime.*
import kotlinx.datetime.format.*

/**
 * Format the current timestamp in the format "YYYY-MM-DDTHH:MM:SSZ"
 */
fun formatCurrentTimestamp() : String {
    val currentDateTime = getNow()
    return currentDateTime.format(
        LocalDateTime.Format {
            year()
            char('-')
            monthNumber()
            char('-')
            dayOfMonth()
            char('T')
            hour()
            char(':')
            minute()
            char(':')
            second()
            char('Z')
        }
    )
}

fun getNow() : LocalDateTime {
    return Clock.System.now().toLocalDateTime(TimeZone.UTC)
}

fun getEpochMillis() : Long {
    return Clock.System.now().toEpochMilliseconds()
}