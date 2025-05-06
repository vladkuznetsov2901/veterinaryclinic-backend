package database.doctors

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.javatime.time

object DoctorSchedule : Table("doctor_schedule") {
    val id = integer("id").autoIncrement()
    val doctorId = integer("doctor_id").references(Doctors.doctorId)
    val date = date("date")
    val startTime = time("start_time")
    val endTime = time("end_time")
    val isBooked = bool("is_booked").default(false)

    override val primaryKey = PrimaryKey(id)
}
