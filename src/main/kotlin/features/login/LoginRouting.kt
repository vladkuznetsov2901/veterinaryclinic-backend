package features.login
import database.tokens.Tokens
import features.recovery.EmailService
import features.recovery.RecoverRequest
import features.recovery.VerifyCodeRequest
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureLoginRouting() {
    routing {
        val recoveryCodes = mutableMapOf<String, Pair<String, Long>>() // email -> (code, expirationTime)

        post("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }

        get("/get_user_id_by_token") {
            val token = call.request.queryParameters["token"]

            if (token.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Token is required")
                return@get
            }

            val userId = Tokens.getUserIdByToken(token)

            if (userId != null) {
                call.respond(HttpStatusCode.OK, mapOf("userId" to userId))
            } else {
                call.respond(HttpStatusCode.NotFound, "User not found for provided token")
            }
        }
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



    }
}

fun generateRecoveryCode(): String {
    return (100000..999999).random().toString()
}
