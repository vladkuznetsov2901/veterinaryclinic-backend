package features.doctors

import io.ktor.server.routing.*
import database.doctors.Doctors
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Route.doctorRoutes() {
    get("/doctors") {
        val doctors = Doctors().getAllDoctorsWithSpecialization()
        call.respond(doctors)
    }
}
