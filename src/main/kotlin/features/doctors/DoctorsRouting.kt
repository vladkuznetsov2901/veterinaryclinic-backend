package features.doctors

import io.ktor.server.routing.*
import database.doctors.DoctorsRepository
import io.ktor.server.application.*
import io.ktor.server.response.*

fun Route.doctorRoutes() {
    get("/doctors") {
        val doctorsRepository = DoctorsRepository.getAllDoctorsWithSpecialization()
        call.respond(doctorsRepository)
    }
    post("/doctor_login") {
        val controller = DoctorLoginController(call)
        controller.performLogin()
    }

}
