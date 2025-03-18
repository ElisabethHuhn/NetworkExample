package org.elisabethhuhn.networkexample.log.presentation

data class LogClientState(
    var requestResult: String = "",
    var errorResult: String = "",
    val lastBufferMessage: String = "",
    val bufferLength: Int = 1,
    val bufferDuration: Long = 1000L, // 1 second
)
