package database.prescription

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object Medications : Table("medications") {
    val medicationId = integer("medication_id")
    val medicationName = text("medication_name")
    val medicationDosage = text("medication_dosage").nullable()
    val medicationForm = text("medication_form").nullable()
    val medicationInstruction = text("medication_instruction").nullable()
    val imageUrl = text("image_url").nullable()
}
