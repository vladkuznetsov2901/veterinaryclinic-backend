package database.chats

import database.DatabaseFactory
import database.DatabaseFactory.dbQuery
import database.DatabaseFactory.execAndMap
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ChatRepository {

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO> = dbQuery {
        Chats.selectAll().where { Chats.userId eq userId }.map {
            ChatDTO(
                chatId = it[Chats.chatId],
                userId = it[Chats.userId],
                doctorId = it[Chats.doctorId],
                createdAt = it[Chats.createdAt].toString()
            )
        }
    }

    suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO> = dbQuery {
        Messages.selectAll().where { Messages.chatId eq chatId }.orderBy(Messages.sentAt to SortOrder.ASC).map {
            MessageDTO(
                messageId = it[Messages.messageId],
                chatId = it[Messages.chatId],
                senderType = it[Messages.senderType],
                messageText = it[Messages.messageText],
                sentAt = it[Messages.sentAt].toString(),
                isRead = it[Messages.isRead]
            )
        }
    }

    fun sendMessage(chatId: Int, senderType: String, messageText: String) {
        transaction {
            Messages.insert {
                it[Messages.chatId] = chatId
                it[Messages.senderType] = senderType
                it[Messages.messageText] = messageText
            }
        }

    }

    fun createChat(userId: Int, doctorId: Int) {
        transaction {
            Chats.insert {
                it[Chats.userId] = userId
                it[Chats.doctorId] = doctorId
            }
        }
    }

    suspend fun getChatsWithInfoByUserId(userId: Int): List<FullChatDTO> {
        return DatabaseFactory.dbQuery {
            val query = """
                    SELECT 
                        c.chat_id,
                        d.doctor_id,
                        d.doctor_name || ' ' || d.doctor_surname || ' (' || s.specialization_title || ')' AS doctor_name,
                        m.message_text AS last_message,
                        m.sent_at AS last_message_time
                    FROM chats c
                    JOIN doctors d ON c.doctor_id = d.doctor_id
                    JOIN doctors_specialization s ON d.doctor_specialization = s.specialization_id
                    LEFT JOIN LATERAL (
                        SELECT message_text, sent_at
                        FROM messages
                        WHERE chat_id = c.chat_id
                        ORDER BY sent_at DESC
                        LIMIT 1
                    ) m ON true
                    WHERE c.user_id = ?
                    ORDER BY m.sent_at DESC

        """.trimIndent()

            execAndMap(query, listOf(userId)) { row ->
                FullChatDTO(
                    chatId = row.getInt("chat_id"),
                    doctorId = row.getInt("doctor_id"),
                    doctorName = row.getString("doctor_name"),
                    doctorImageUrl = "",
                    lastMessage = row.getString("last_message"),
                    lastMessageTime = row.getString("last_message_time")
                )
            }
        }
    }


}