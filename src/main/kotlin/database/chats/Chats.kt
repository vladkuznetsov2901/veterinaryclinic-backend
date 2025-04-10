package database.chats

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.CurrentDateTime
import org.jetbrains.exposed.sql.javatime.datetime

object Chats : Table("chats") {
    val chatId = integer("chat_id").autoIncrement()
    val userId = integer("user_id")
    val doctorId = integer("doctor_id")
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime)
    override val primaryKey = PrimaryKey(chatId)
}