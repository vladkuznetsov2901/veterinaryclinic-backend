package database.store

import io.minio.MinioClient

object MinioClientProvider {
    val client = MinioClient.builder()
        .endpoint("http://localhost:9000")
        .credentials("minioadmin", "minioadmin")
        .build()
}