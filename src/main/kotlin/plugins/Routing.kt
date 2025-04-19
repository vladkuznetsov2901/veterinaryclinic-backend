package plugins

import features.chats.chatRoutes
import features.doctors.doctorRoutes
import features.get_specialization.getSpecializationsRoute
import features.pets.petsRoutes
import features.prescription.prescriptionsRoutes
import features.promo.uploadImageRoute
import features.recovery.configureRestoreRouting
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }

        uploadImageRoute()

        getSpecializationsRoute()

        doctorRoutes()

        chatRoutes()

        configureRestoreRouting()

        petsRoutes()

        prescriptionsRoutes()

    }
}
