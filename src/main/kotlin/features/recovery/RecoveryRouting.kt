package features.recovery

import database.tokens.Tokens
import database.users.Users.updatePassword
import features.recovery.EmailService
import features.recovery.RecoverRequest
import features.recovery.VerifyCodeRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Route.configureRestoreRouting() {
    val recoveryCodes = mutableMapOf<String, Pair<String, Long>>()

    post("/send-code") {
        val request = call.receive<RecoverRequest>()
        val code = generateRecoveryCode()
        val expirationTime = System.currentTimeMillis() + 5 * 60 * 1000

        recoveryCodes[request.email] = code to expirationTime

        EmailService().sendRecoveryCode(request.email, code)

        call.respond(HttpStatusCode.OK, "Код отправлен на почту")
    }

    post("/verify-code") {
        val request = call.receive<VerifyCodeRequest>()
        val stored = recoveryCodes[request.email]

        if (stored == null) {
            call.respond(HttpStatusCode.NotFound, "Код не найден")
            return@post
        }

        val (savedCode, expiresAt) = stored
        if (System.currentTimeMillis() > expiresAt) {
            recoveryCodes.remove(request.email)
            call.respond(HttpStatusCode.Gone, "Код истёк")
            return@post
        }

        if (savedCode != request.code) {
            call.respond(HttpStatusCode.Unauthorized, "Неверный код")
            return@post
        }

        // Можно тут выдать токен или разрешить смену пароля
        call.respond(HttpStatusCode.OK, "Код подтверждён")
    }

    post("/change-password") {
        val request = call.receive<ChangePasswordRequest>()
        val stored = recoveryCodes[request.email]

        if (stored == null || stored.first != request.code || System.currentTimeMillis() > stored.second) {
            call.respond(HttpStatusCode.BadRequest, "Неверный или истекший код")
            return@post
        }

        // Хешируем пароль
//            val hashedPassword = BCrypt.hashpw(request.newPassword, BCrypt.gensalt())

        // Обновляем в БД
        try {
            updatePassword(request.email, request.newPassword)
            recoveryCodes.remove(request.email)
            call.respond(HttpStatusCode.OK, "Пароль успешно изменён")
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Ошибка при обновлении пароля")
        }
    }


}


fun generateRecoveryCode(): String {
    return (100000..999999).random().toString()
}
