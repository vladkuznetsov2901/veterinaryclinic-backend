package features.chats

import database.chats.ChatRepository
import database.chats.CreateChatRequest
import database.chats.SendMessageRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.chatRoutes() {
    route("/chats") {

        // Получение всех чатов по userId
        get("/{userId}") {
            val userId = call.parameters["userId"]?.toIntOrNull()
            if (userId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid user ID")
                return@get
            }

            val chats = ChatRepository.getChatsByUserId(userId)
            call.respond(chats)
        }

        // Получение всех сообщений по chatId
        get("/messages/{chatId}") {
            val chatId = call.parameters["chatId"]?.toIntOrNull()
            if (chatId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid chat ID")
                return@get
            }

            val messages = ChatRepository.getMessagesByChatId(chatId)
            call.respond(messages)
        }

        post("/send_message") {
            val request = try {
                call.receive<SendMessageRequest>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
                return@post
            }

            ChatRepository.sendMessage(
                chatId = request.chatId,
                senderType = request.senderType,
                messageText = request.messageText
            )

            call.respond(HttpStatusCode.OK, "Message sent")
        }

        post {
            val request = try {
                call.receive<CreateChatRequest>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, "Invalid request format")
                return@post
            }

            val newChatId = ChatRepository.createChat(request.userId, request.doctorId)
            call.respond(HttpStatusCode.Created, mapOf("chatId" to newChatId))
        }


    }
}

