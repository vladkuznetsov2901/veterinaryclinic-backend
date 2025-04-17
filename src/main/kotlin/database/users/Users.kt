package database.users

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.javatime.date

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update


object Users : Table("users") {

    val userId = integer("user_id").autoIncrement()
    val userEmail = text("user_email")
    val userPhoneNumber = text("user_phone_number")
    val userPassword = text("user_password")
    val userName = text("user_name")
    val userSurname = text("user_surname")
    val userLastname = text("user_lastname").nullable()
    val userDateOfBirth = date("user_date_of_birth")

    fun insert(userDTO: UserDTO) {
        transaction {
            Users.insert {
                it[userId] = userDTO.userId
                it[userEmail] = userDTO.userEmail
                it[userPhoneNumber] = userDTO.userPhoneNumber
                it[userPassword] = userDTO.userPassword
                it[userName] = userDTO.userFirstname
                it[userSurname] = userDTO.userSurname
                it[userLastname] = userDTO.userLastname
            }
        }
    }

    fun fetchUser(userEmail: String): UserDTO? {
        return try {
            transaction {
                val userModel = Users.selectAll().where { Users.userEmail.eq(userEmail) }.single()
                UserDTO(
                    userId = userModel[userId],
                    userEmail = userModel[Users.userEmail],
                    userPhoneNumber = userModel[userPhoneNumber],
                    userPassword = userModel[userPassword],
                    userFirstname = userModel[userName],
                    userSurname = userModel[userSurname],
                    userLastname = userModel[userLastname],
                    userDateOfBirth = userModel[userDateOfBirth].toString()
                )
            }

        } catch (e: Exception) {
            null
        }

    }

    fun updatePassword(email: String, newPassword: String) {
        transaction {
            Users.update({ userEmail.eq(email)  }) {
                it[userPassword] = newPassword
            }
        }
    }

}