package database.chats

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime
import org.jetbrains.exposed.sql.javatime.CurrentDateTime

object Messages : Table("messages") {
    val messageId = integer("message_id").autoIncrement()
    val chatId = integer("chat_id") references Chats.chatId
    val senderType = varchar("sender_type", 10) // 'user' or 'doctor'
    val messageText = text("message_text")
    val sentAt = datetime("sent_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(messageId)
}