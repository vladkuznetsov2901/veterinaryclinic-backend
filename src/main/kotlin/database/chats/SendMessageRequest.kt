package database.chats
import kotlinx.serialization.Serializable

@Serializable
data class SendMessageRequest(
    val chatId: Int,
    val senderType: String, // "user" или "doctor"
    val messageText: String
)

