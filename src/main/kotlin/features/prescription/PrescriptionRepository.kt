package features.prescription

import database.DatabaseFactory.dbQuery
import database.prescription.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.javatime.CurrentDate
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

object PrescriptionRepository {
    fun getActivePrescriptionsByPet(petId: Int): List<PrescriptionDto> = transaction {
        val prescriptions = Prescriptions
            .selectAll().where { Prescriptions.petId eq petId }
            .map { it[Prescriptions.prescriptionId] }

        prescriptions.mapNotNull { prescId ->
            val prescriptionRow = Prescriptions
                .selectAll()
                .where { Prescriptions.prescriptionId eq prescId }
                .singleOrNull() ?: return@mapNotNull null

            val items = PrescriptionItems
                .innerJoin(Medications)
                .selectAll().where {
                    PrescriptionItems.prescriptionId eq prescId
                }
                .map { row ->
                    val itemId = row[PrescriptionItems.itemId]

                    val medication = MedicationDto(
                        medicationName = row[Medications.medicationName],
                        medicationDosage = row[Medications.medicationDosage],
                        imageUrl = row[Medications.imageUrl]
                    )

                    val schedule = MedicationSchedule
                        .selectAll().where { MedicationSchedule.item eq itemId }
                        .map {
                            MedicationScheduleDto(
                                scheduleId = it[MedicationSchedule.scheduleId],
                                plannedTime = it[MedicationSchedule.plannedTime].toString(),
                                isTaken = it[MedicationSchedule.isTaken],
                                takenTime = it[MedicationSchedule.takenTime]?.toString()
                            )
                        }

                    PrescriptionItemDto(
                        medication = medication,
                        dosage = row[PrescriptionItems.dosage],
                        frequency = row[PrescriptionItems.frequency],
                        durationDays = row[PrescriptionItems.durationDays],
                        startDate = row[PrescriptionItems.startDate].toString(),
                        endDate = row[PrescriptionItems.endDate].toString(),
                        notes = row[PrescriptionItems.notes],
                        schedule = schedule
                    )
                }


            PrescriptionDto(
                prescriptionId = prescriptionRow[Prescriptions.prescriptionId],
                diagnosis = prescriptionRow[Prescriptions.diagnosis],
                notes = prescriptionRow[Prescriptions.notes],
                date = prescriptionRow[Prescriptions.prescriptionDate].toString(),
                doctorId = prescriptionRow[Prescriptions.doctorId],
                items = items
            )

        }
    }

    fun markScheduleAsTaken(scheduleId: Int): Boolean = transaction {
        MedicationSchedule.update({ MedicationSchedule.scheduleId eq scheduleId }) {
            it[isTaken] = not(isTaken)
            it[takenTime] = LocalDateTime.now()
        } > 0
    }
}

