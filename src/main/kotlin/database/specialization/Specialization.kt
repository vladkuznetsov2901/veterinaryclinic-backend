package database.specialization

import org.jetbrains.exposed.sql.Table

object DoctorsSpecializationTable : Table("doctors_specialization") {
    val id = integer("specialization_id").autoIncrement()
    val title = text("specialization_title")

    override val primaryKey = PrimaryKey(id)
}