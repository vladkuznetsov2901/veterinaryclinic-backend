package database.tokens

data class TokenDTO(
    val id: Int? = null,
    val login: String,
    val token: String,
)