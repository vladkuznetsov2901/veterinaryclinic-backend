package database.users

data class UserDTO(
    val userId: Int,
    val userEmail: String,
    val userPhoneNumber: String,
    val userPassword: String,
    val userFirstname: String,
    val userSurname: String,
    val userLastname: String?,
    val userDateOfBirth: String,
    val role: String
)