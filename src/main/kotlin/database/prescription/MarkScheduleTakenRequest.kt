package database.prescription

@kotlinx.serialization.Serializable
data class MarkScheduleTakenRequest(
    val scheduleId: Int
)


