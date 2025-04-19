package features.prescription

import io.ktor.http.*
import io.ktor.server.application.*
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
}
