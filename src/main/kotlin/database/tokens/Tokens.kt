package database.tokens

import database.users.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

object Tokens : Table("tokens") {

    val id = integer("id").autoIncrement()
    val userId = integer("user_id").references(Users.userId)
    val token = text("token")

    override val primaryKey = PrimaryKey(id)

    fun insert(tokenDTO: TokenDTO) {
        transaction {
            Tokens.insert {
                it[userId] = tokenDTO.userId
                it[token] = tokenDTO.token
            }
        }
    }


    fun getUserIdByToken(token: String?): Int? {
        return transaction {
            Tokens
                .selectAll().where { Tokens.token eq token!! }
                .singleOrNull()
                ?.get(userId)
        }
    }


}