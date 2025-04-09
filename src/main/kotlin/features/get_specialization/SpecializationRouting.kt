package features.get_specialization

import database.specialization.DoctorsSpecializationTable
import database.specialization.SpecializationDTO
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun Route.getSpecializationsRoute() {
    get("/specializations") {
        val specializations = transaction {
            DoctorsSpecializationTable
                .selectAll()
                .map {
                    SpecializationDTO(
                        id = it[DoctorsSpecializationTable.id],
                        title = it[DoctorsSpecializationTable.title]
                    )
                }
        }
        call.respond(specializations)
    }
}
