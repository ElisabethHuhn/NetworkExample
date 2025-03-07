package org.elisabethhuhn.networkexample

import org.elisabethhuhn.networkexample.model.loggermodel.LogEntry
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

//https://medium.com/@juricavoda/kotlin-for-web-development-using-ktor-for-server-side-apps-204c5b72da0a
//
//https://medium.com/@michael.avoyan/mastering-kotlin-ktor-for-backend-development-8c4e5573ee72
// https://ktor.io/docs/server-serialization.html#receive_send_data

// Authentication and authorization https://ktor.io/docs/server-auth.html
// CORS https://ktor.io/docs/server-cors.html

fun main() {
    embeddedServer(
        factory = Netty,
        port = SERVER_PORT,
        host = "0.0.0.0",
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    // Install ContentNegotiation before routing and security configurations
    install(ContentNegotiation) {
        json(
            Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            }
        )
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
//        allowHeader("MyCustomHeader")
//        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    //authentication and authorization
//    install(Authentication) {
//        basic("myBasicAuth") {
//            realm = "Ktor Server"
//            validate { credentials ->
//                if (credentials.name == "user" && credentials.password == "password") {
//                    UserIdPrincipal(credentials.name)
//                } else {
//                    null
//                }
//            }
//        }
//    }
//    install(Authentication) {
//        jwt("auth-jwt") {
//            verifier(JwtProvider.verifier)
//            validate { credentials ->
//                if (credentials.payload.audience.contains("your-audience")) JWTPrincipal(credentials.payload) else null
//            }
//        }
//    }
    //Use authenticate("auth-jwt") to secure specific routes, ensuring only authorized users access sensitive areas.


    // Routes define paths that your server responds to
    // Routes are processed in the order they are defined.
    routing {
        get("/") {
//            call.respondText("Ktor: ${Greeting().greet()}")
            call.respondText("Ktor: Hello, World!")
        }
        post<List<LogEntry>>(path = "/log") {
            Json.decodeFromString<Message>(messageString)
            val result = call.receive<>()
        }
        //use coroutines
//        get("/data") {
//            val result = async { dataService.fetchData() }.await()
//            call.respond(result)
//        }
        //Wrap asynchronous calls in try-catch blocks to handle errors gracefully and prevent resource leaks.
        //define other routes here
    }
}