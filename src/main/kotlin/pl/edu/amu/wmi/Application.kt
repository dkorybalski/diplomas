package pl.edu.amu.wmi

import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import pl.edu.amu.wmi.plugins.*

fun main() {
    embeddedServer(Netty, port = 3300, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureRouting()
    install(ContentNegotiation) {
        jackson()
    }
}