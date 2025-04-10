package database.chats

import kotlinx.serialization.Serializable

@Serializable
data class MessageDTO(
    val messageId: Int,
    val chatId: Int,
    val senderType: String,
    val messageText: String,
    val sentAt: String
)