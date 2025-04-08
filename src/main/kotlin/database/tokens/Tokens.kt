package database.tokens

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {

    val id = integer("id").autoIncrement()
    val login = text("login")
    val token = text("token")

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[login] = tokenDTO.login
                it[token] = tokenDTO.token
            }
        }
    }

    fun getUserIdByToken(token: String?): String? {
        return transaction {
            Tokens.selectAll().where { Tokens.token.eq(token!!) }.singleOrNull()?.get(login)
        }
    }

}