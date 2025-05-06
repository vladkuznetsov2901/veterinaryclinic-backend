package features.doctors

import database.doctors.BookSlotDTO
import database.doctors.DoctorScheduleRepository
import io.ktor.server.routing.*
import database.doctors.DoctorsRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
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

    get("/doctors/{id}/available-slots") {
        val doctorId = call.parameters["id"]?.toIntOrNull()
        if (doctorId == null) {
            call.respond(HttpStatusCode.BadRequest, "Invalid doctor ID")
            return@get
        }

        val slots = DoctorScheduleRepository.getAvailableSlotsForWeek(doctorId)
        call.respond(slots)
    }

    post("/schedule/book") {
        val slot = call.receive<BookSlotDTO>()
        val success = DoctorScheduleRepository.bookSlot(slot)
        if (success) {
            call.respond(HttpStatusCode.OK, "Slot booked successfully")
        } else {
            call.respond(HttpStatusCode.Conflict, "Slot already booked or invalid")
        }
    }


}
