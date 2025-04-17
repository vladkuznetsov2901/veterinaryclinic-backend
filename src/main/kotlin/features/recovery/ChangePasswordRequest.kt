package features.recovery

@kotlinx.serialization.Serializable
data class ChangePasswordRequest(
    val email: String,
    val code: String,
    val newPassword: String
)
