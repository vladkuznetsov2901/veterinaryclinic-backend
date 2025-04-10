package database.chats

import kotlinx.serialization.Serializable

@Serializable
data class ChatDTO(
    val chatId: Int,
    val userId: Int,
    val doctorId: Int,
    val createdAt: String
)
