package features.doctors


import database.doctors.DoctorsRepository
import database.tokens.TokenDTO
import database.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import java.util.*

class DoctorLoginController(private val call: ApplicationCall) {
    suspend fun performLogin() {
        val receive = call.receive<DoctorReceiveRemote>()
        val doctorDTO = DoctorsRepository.fetchDoctor(receive.login)

        if (doctorDTO == null) {
            call.respond(HttpStatusCode.Conflict, "Doctor not found")
        } else {
            if (doctorDTO.doctorPassword == receive.password) {
                val token = UUID.randomUUID().toString()
                Tokens.insert(
                    TokenDTO(
                        userId = doctorDTO.doctorId, // можно выделить doctorId отдельно в токенах
                        token = token
                    )
                )
                call.respond(DoctorResponseRemote(token = token, doctorId = doctorDTO.doctorId))
            } else {
                call.respond(HttpStatusCode.BadRequest, "Invalid password")
            }
        }
    }
}
