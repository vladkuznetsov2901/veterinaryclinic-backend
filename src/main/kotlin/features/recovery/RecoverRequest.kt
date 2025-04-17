package features.recovery

@kotlinx.serialization.Serializable
data class RecoverRequest(
    val email: String
)

