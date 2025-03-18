package org.elisabethhuhn.networkexample

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    // Install ContentNegotiation before routing and security configurations

    //Content Negotiation matches:
    //      the types of content the client can render against
    //      the types of content the server can provide.
    // in HTTP
    //       the client signals content types it can render through the Accept header
    //             several content types (HTML, XML, image, etc) can be indicated
    //       the server signals content types it can provide through the Content-Type header

    // the following installs the ContentNegotiation plugin, and configures the kotlinx.serialization plugin
    // when the client sends requests, the server can send back json
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }
    routing {
        get("/json/kotlinx-serialization") {
                call.respond(mapOf("hello" to "world"))
            }
    }
}
