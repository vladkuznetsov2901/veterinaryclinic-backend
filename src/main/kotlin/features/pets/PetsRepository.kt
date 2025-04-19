package features.pets

import database.pets.PetResponseDto
import database.pets.Pets
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object PetsRepository {
    fun getPetsByUserId(userId: Int): List<PetResponseDto> {
        return transaction {
            Pets.selectAll().where { Pets.userId eq userId }
                .map {
                    PetResponseDto(
                        id = it[Pets.petId],
                        name = it[Pets.petName],
                        species = it[Pets.petSpecies],
                        isPureBreed = it[Pets.petBreed],
                        dateOfBirth = it[Pets.petDateOfBirth].toString()
                    )
                }
        }
    }
}
