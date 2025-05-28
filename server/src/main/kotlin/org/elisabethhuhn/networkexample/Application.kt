package org.elisabethhuhn.networkexample



import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

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
        host = BASE_URL,
        module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {

    configureSerialization()
    configureRouting()




    routing {
        get("/") {
            call.respondText("Ktor: Hello, World!")
        }
     }




}