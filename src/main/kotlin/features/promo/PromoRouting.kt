package features.promo

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.http.content.*
import java.io.File
import java.util.*

fun Route.uploadImageRoute() {
    get("/promo_images") {
        val uploadsDir = File("uploads")
        if (!uploadsDir.exists()) {
            call.respond(HttpStatusCode.NotFound, "uploads folder not found")
            return@get
        }

        val imageUrls = uploadsDir.listFiles { file -> file.isFile }?.map { file ->
            "http://10.0.2.2:8080/uploads/${file.name}"
        } ?: emptyList()

        call.respond(imageUrls)
    }

}
