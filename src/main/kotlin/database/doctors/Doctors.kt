package database.doctors


import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Doctors : Table("doctors") {
    val doctorId = integer("doctor_id")
    val doctorName = text("doctor_name")
    val doctorSurname = text("doctor_surname")
    val doctorLastname = text("doctor_lastname").nullable()
    val doctorDateOfBirth = date("doctor_date_of_birth")
    val doctorPhoneNumber = text("doctor_phone_number")
    val doctorEmail = text("doctor_email")
    val doctorSpecialization = integer("doctor_specialization")
    val doctorRate = decimal("doctor_rate", 2, 1)
    val doctorStartWorkDate = date("doctor_start_work_date")
    val doctorPassword = text("doctor_password")

    override val primaryKey = PrimaryKey(doctorId)
}

