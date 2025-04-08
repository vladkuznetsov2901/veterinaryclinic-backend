package features.login
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.routing.*


fun Application.configureLoginRouting() {
    routing {
        post("/login") {
            val loginController = LoginController(call)
            loginController.performLogin()
        }
    }
}