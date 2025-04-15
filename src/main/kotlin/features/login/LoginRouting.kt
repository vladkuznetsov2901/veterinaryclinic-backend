package features.login
import database.tokens.Tokens
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureLoginRouting() {
    routing {
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

    }
}