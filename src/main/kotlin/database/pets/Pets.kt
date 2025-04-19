package database.pets

import database.pets.Pets.integer
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date

object Pets : Table("pets") {
    val petId = integer("pet_id")
    val petName = text("pet_name")
    val petSpecies = text("pet_species")
    val petBreed = bool("pet_breed")
    val petDateOfBirth = date("pet_date_of_birth")
    val userId = integer("user_id").nullable()
}


