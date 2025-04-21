package database.prescription

@kotlinx.serialization.Serializable
data class MedicationScheduleDto(
    val scheduleId: Int,
    val plannedTime: String,
    val isTaken: Boolean,
    val takenTime: String?
)
