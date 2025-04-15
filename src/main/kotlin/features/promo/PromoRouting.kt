package features.promo

import database.store.MinioClientProvider
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.minio.MinioClient
import io.minio.ListObjectsArgs

fun Route.uploadImageRoute() {
    get("/promo_images") {
        val minioClient = MinioClientProvider.client

        val bucketName = "promo-images"

        try {
            val result = minioClient.listObjects(
                ListObjectsArgs.builder()
                    .bucket(bucketName)
                    .prefix("promo")
                    .recursive(true)
                    .build()
            )

            val imageUrls = result.asSequence()
                .mapNotNull { it.get()?.objectName() }
                .filter { it.endsWith(".png") }
                .map { "http://10.0.2.2:9000/$bucketName/$it" }
                .toList()

            call.respond(imageUrls)
        } catch (e: Exception) {
            call.respond(HttpStatusCode.InternalServerError, "Failed to fetch images: ${e.message}")
        }
    }
}
