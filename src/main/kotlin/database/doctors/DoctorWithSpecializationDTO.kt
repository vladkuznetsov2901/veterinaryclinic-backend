package database.doctors

import kotlinx.serialization.Serializable

@Serializable
data class DoctorWithSpecializationDTO(
    val doctorId: Int,
    val name: String,
    val surname: String,
    val lastname: String?,
    val dateOfBirth: String,
    val phoneNumber: String,
    val email: String,
    val specialization: String,
    val rate: Double,
    val startWorkDate: String,
    val doctorPassword: String,
)