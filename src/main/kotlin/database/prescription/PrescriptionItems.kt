package database.prescription

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object PrescriptionItems : Table("prescription_items") {
    val itemId = integer("item_id")
    val prescriptionId = integer("prescription_id").references(Prescriptions.prescriptionId)
    val medicationId = integer("medication_id").references(Medications.medicationId)
    val dosage = text("dosage").nullable()
    val frequency = text("frequency").nullable()
    val durationDays = integer("duration_days").nullable()
    val startDate = date("start_date").nullable()
    val endDate = date("end_date").nullable()
    val notes = text("notes").nullable()
}
