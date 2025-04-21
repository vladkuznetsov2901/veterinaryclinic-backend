package database.prescription

import kotlinx.serialization.Serializable

@Serializable
data class MedicationDto(
    val medicationName: String,
    val medicationDosage: String,
    val imageUrl: String?
)
