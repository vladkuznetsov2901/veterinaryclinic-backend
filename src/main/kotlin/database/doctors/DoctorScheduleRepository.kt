package database.doctors

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate
import java.time.LocalTime

object DoctorScheduleRepository {

    fun getAvailableSlotsForWeek(doctorId: Int): List<DoctorSlotDTO> {
        return transaction {
            val today = LocalDate.now()
            val endDate = today.plusDays(7)

            DoctorSchedule
                .selectAll().where {
                    (DoctorSchedule.doctorId eq doctorId) and
                            (DoctorSchedule.date greaterEq today) and
                            (DoctorSchedule.date lessEq endDate) and
                            (DoctorSchedule.isBooked eq false)
                }
                .map {
                    DoctorSlotDTO(
                        date = it[DoctorSchedule.date].toString(),
                        startTime = it[DoctorSchedule.startTime].toString(),
                        endTime = it[DoctorSchedule.endTime].toString()
                    )
                }
        }
    }

    fun bookSlot(slot: BookSlotDTO): Boolean {
        return try {
            transaction {
                val updated = DoctorSchedule.update({
                    (DoctorSchedule.doctorId eq slot.doctorId) and
                            (DoctorSchedule.date eq LocalDate.parse(slot.date)) and
                            (DoctorSchedule.startTime eq LocalTime.parse(slot.startTime)) and
                            (DoctorSchedule.isBooked eq false)
                }) {
                    it[isBooked] = true
                }

                updated > 0
            }
        } catch (e: Exception) {
            false
        }
    }

}
