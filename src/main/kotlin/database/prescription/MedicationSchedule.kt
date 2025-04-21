package database.prescription

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object MedicationSchedule : Table("medication_schedule") {
    val scheduleId = integer("schedule_id")
    val item = integer("item_id").references(PrescriptionItems.itemId)
    val plannedTime = datetime("planned_time")
    val isTaken = bool("is_taken").default(false)
    val takenTime = datetime("taken_time").nullable()

    override val primaryKey = PrimaryKey(MedicationSchedule.scheduleId)

}
