package database.chats

import database.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object ChatRepository {

    suspend fun getChatsByUserId(userId: Int): List<ChatDTO> = dbQuery {
        Chats.selectAll().where { Chats.userId eq userId }
            .map {
                ChatDTO(
                    chatId = it[Chats.chatId],
                    userId = it[Chats.userId],
                    doctorId = it[Chats.doctorId],
                    createdAt = it[Chats.createdAt].toString()
                )
            }
    }

    suspend fun getMessagesByChatId(chatId: Int): List<MessageDTO> = dbQuery {
        Messages.selectAll().where { Messages.chatId eq chatId }
            .orderBy(Messages.sentAt to SortOrder.ASC)
            .map {
                MessageDTO(
                    messageId = it[Messages.messageId],
                    chatId = it[Messages.chatId],
                    senderType = it[Messages.senderType],
                    messageText = it[Messages.messageText],
                    sentAt = it[Messages.sentAt].toString()
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


}