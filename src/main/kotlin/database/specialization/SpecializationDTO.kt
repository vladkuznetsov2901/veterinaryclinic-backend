package database.specialization

import kotlinx.serialization.Serializable

@Serializable
data class SpecializationDTO(
    val id: Int,
    val title: String
)

