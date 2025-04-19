package database.prescription

import database.doctors.Doctors
import database.pets.Pets
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.javatime.date

object Prescriptions : Table("prescriptions") {
    val prescriptionId = integer("prescription_id")
    val petId = integer("pet_id").references(Pets.petId)
    val doctorId = integer("doctor_id").references(Doctors.doctorId)
    val prescriptionDate = date("prescription_date").defaultExpression(CurrentDate)
    val diagnosis = text("diagnosis").nullable()
    val notes = text("notes").nullable()
}
