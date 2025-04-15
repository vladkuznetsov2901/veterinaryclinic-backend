package database.chats

import kotlinx.serialization.Serializable

@Serializable
data class FullChatDTO(
    val chatId: Int,
    val doctorId: Int,
    val doctorName: String,
    val doctorImageUrl: String?,
    val lastMessage: String?,
    val lastMessageTime: String?
)

