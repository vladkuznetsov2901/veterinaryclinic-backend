package database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import java.sql.ResultSet

object DatabaseFactory {
    fun init() {
        Database.connect(
            "jdbc:postgresql://localhost:5432/veterinaryclinic",
            driver = "org.postgresql.Driver",
            "postgres",
            "5891"
        )
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction { block() }

    fun <T> execAndMap(
        sql: String,
        params: List<Any?> = emptyList(),
        map: (ResultSet) -> T
    ): List<T> {
        val result = mutableListOf<T>()
        TransactionManager.current().connection.prepareStatement(sql, false).apply {
            params.forEachIndexed { index, param ->
                if (param != null) {
                    set(index + 1, param)
                }
            }
            val rs = executeQuery()
            while (rs.next()) {
                result.add(map(rs))
            }
        }
        return result
    }
}