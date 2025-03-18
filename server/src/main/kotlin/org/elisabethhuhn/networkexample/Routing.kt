package org.elisabethhuhn.networkexample


import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.contentLength
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.elisabethhuhn.networkexample.logger.data.LogEntryDto
import org.elisabethhuhn.networkexample.logger.domain.LoggingMsgSeverity


val logEntryStorage = mutableListOf<LogEntryDto>()

fun Application.configureRouting() {
    routing {
        // Routes define paths that your server responds to
        // Routes are processed in the order they are defined.
        // The first route that matches the request is the one that is executed.

        // The get function defines a route that responds to GET requests.
        get("/") {
            call.respondText("Ktor: Hello, World!")
        }

        // Static resources are files that are served directly by the server without any processing.

        // The staticResources function is a helper function that serves static resources from a directory.
        // The first parameter is the path to the directory containing the resources.
        // The second parameter is the path to the URL that the resources will be served from.
        // In this case, the resources are served from the static directory and are available at the /static URL.
        staticResources("static", "static")

        route("/log") {
            get {
                call.respond(logEntryStorage)
            }

            post {
                when {
                    //check if the request body is empty
                    call.request.contentLength() == 0L -> call.respondText("Request body is empty", status = HttpStatusCode.BadRequest)

                    // get the json from the request
                    !call.request.content.isJson() ->
                        call.respondText("Request body must be json", status = HttpStatusCode.BadRequest)

                    //validate top level json is a list
                    call.request.content.isJsonArray() -> call.respondText("Request body must be an array of log entries", status = HttpStatusCode.BadRequest)

                    //parse the json into a list of LogEntry objects
                    else -> {
                        val logEntries = call.receive<List<LogEntryDto>>()

                        //validate the LogEntry objects
                        val invalidEntries = logEntries.filter { logEntry ->
                            logEntry.message == null ||
                                    logEntry.severity == null ||
                                    logEntry.timestamp == null
                        }
                        if (invalidEntries.isNotEmpty()) {
                            call.respondText("Missing required fields: ${invalidEntries.joinToString { it.message.toString() }}", status = HttpStatusCode.BadRequest)
                        }

                        val invalidSeverity = logEntries.filter { logEntry ->
                            isSeverityValid(logEntry.severity ?: "")
                        }
                        if (invalidSeverity.isNotEmpty()) {
                            call.respondText("Invalid severity level. Must be one of: ${LoggingMsgSeverity.entries.joinToString()}", status = HttpStatusCode.BadRequest)
                        }

                        val invalidTimestamp = logEntries.filter { isTimestampValid(it.timestamp)}
                        if (invalidTimestamp.isNotEmpty()) {
                            call.respondText("Invalid timestamp format", status = HttpStatusCode.BadRequest)
                        }

                        //store the log entries in the database
                        logEntries.forEach { logEntryStorage.add(it) }

                        //respond with a success message
                        call.respondText("Log entries stored successfully", status = HttpStatusCode.OK)
                    }
                }
            }
        }



        /*
         post<List<LogEntry>>(path = "/log") {
             Json.decodeFromString<Message>(messageString)
             val result = call.receive<>()
         }
          */

        //use coroutines
//        get("/data") {
//            val result = async { dataService.fetchData() }.await()
//            call.respond(result)
//        }
        //Wrap asynchronous calls in try-catch blocks to handle errors gracefully and prevent resource leaks.
        //define other routes here

        //customer example
        //        get("/customer/{id}") {
//            val id = call.parameters["id"]
//            val customer: Customer = customerStorage.find { it.id == id!!.toInt() }!!
//            call.respond(customer)
//        }
//
//        post("/customer") {
//            val customer = call.receive<Customer>()
//            customerStorage.add(customer)
//            call.respondText("Customer stored correctly", status = HttpStatusCode.Created)
//        }

    }
}

fun isSeverityValid(severity: String): Boolean {
    return severity in LoggingMsgSeverity.entries.map { it.name }
}
fun isTimestampValid(timestamp: String?): Boolean {
   try {
       if (timestamp.isNullOrEmpty()) return false
       val longTs = timestamp.toLong()
       return true
   } catch (e: Exception) {
       return false
   }
}
