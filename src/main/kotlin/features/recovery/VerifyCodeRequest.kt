package features.recovery

@kotlinx.serialization.Serializable
data class VerifyCodeRequest(
    val email: String,
    val code: String
)

