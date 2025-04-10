package database.chats
import kotlinx.serialization.Serializable

@Serializable
data class CreateChatRequest(
    val userId: Int,
    val doctorId: Int
)
