package database.prescription

import kotlinx.serialization.Serializable

@Serializable
data class PrescriptionDto(
    val prescriptionId: Int,
    val diagnosis: String?,
    val notes: String?,
    val date: String,
    val doctorId: Int,
    val items: List<PrescriptionItemDto>
)
