package database.pets

import kotlinx.serialization.Serializable

@Serializable
data class PetResponseDto(
    val id: Int,
    val name: String,
    val species: String,
    val isPureBreed: Boolean,
    val dateOfBirth: String
)
