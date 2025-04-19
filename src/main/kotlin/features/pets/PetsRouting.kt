package features.pets

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.petsRoutes() {
    route("/pets") {
        get("/{userId}") {
            val userId = call.parameters["userId"]?.toInt()
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid or missing userId")
                return@get
            }

            val pets = PetsRepository.getPetsByUserId(userId)
            call.respond(pets)
        }
    }
}
