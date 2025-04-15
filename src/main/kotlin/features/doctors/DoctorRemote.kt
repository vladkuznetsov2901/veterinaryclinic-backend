package features.doctors

import kotlinx.serialization.Serializable

@Serializable
data class DoctorReceiveRemote(
    val login: String,
    val password: String
)

@Serializable
data class DoctorResponseRemote(
    val token: String,
    val doctorId: Int
)