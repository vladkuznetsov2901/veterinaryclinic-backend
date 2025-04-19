package database.prescription

import kotlinx.serialization.Serializable

@Serializable
data class PrescriptionItemDto(
    val medication: MedicationDto,
    val dosage: String?,
    val frequency: String?,
    val durationDays: Int?,
    val startDate: String,
    val endDate: String,
    val notes: String?,
    val schedule: List<MedicationScheduleDto>? = null
)
