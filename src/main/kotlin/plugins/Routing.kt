package plugins

import features.chats.chatRoutes
import features.doctors.doctorRoutes
import features.get_specialization.getSpecializationsRoute
import features.promo.uploadImageRoute
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        static("/uploads") {
            files("uploads")
        }

        uploadImageRoute()

        getSpecializationsRoute()

        doctorRoutes()

        chatRoutes()
    }
}
