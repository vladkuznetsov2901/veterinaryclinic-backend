package features.prescription

import database.prescription.MarkScheduleTakenRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.prescriptionsRoutes() {
    route("/prescriptions") {
        get("/active/{petId}") {
            val petId = call.parameters["petId"]?.toIntOrNull()
            if (petId == null) {
                call.respond(HttpStatusCode.BadRequest, "Missing or invalid petId")
                return@get
            }

            val activePrescriptions = PrescriptionRepository.getActivePrescriptionsByPet(petId)
            call.respond(activePrescriptions)
        }
    }

    route("/schedule") {
        post("/mark-taken/{scheduleId}") {
            val scheduleId = call.parameters["scheduleId"]?.toIntOrNull()

            if (scheduleId != null) {
                val updated = PrescriptionRepository.markScheduleAsTaken(scheduleId)

                if (updated) {
                    call.respond(HttpStatusCode.OK, "Schedule marked as taken")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Schedule item not found")
                }
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid scheduleId")
            }
        }
    }

}
