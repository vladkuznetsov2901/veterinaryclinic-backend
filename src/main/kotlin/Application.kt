

import features.login.configureLoginRouting
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import plugins.configureSerialization
import plugins.configureRouting

import org.jetbrains.exposed.sql.Database

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/veterinaryclinic",
        driver = "org.postgresql.Driver",
        "postgres",
        "5891"
    )

    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        module()
    }.start(wait = true)

}

fun Application.module() {
    configureRouting()
    configureSerialization()
    configureLoginRouting()

}