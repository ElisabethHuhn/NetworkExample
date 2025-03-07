package org.elisabethhuhn.networkexample.util

import org.elisabethhuhn.networkexample.util.Error

enum class NetworkError : Error {
    REQUEST_TIMEOUT,
    UNAUTHORIZED,
    CONFLICT,
    TOO_MANY_REQUESTS,
    NO_INTERNET,
    PAYLOAD_TOO_LARGE,
    SERVER_ERROR,
    SERIALIZATION,
    CONNECTION_FAILED,
    UNKNOWN;
}