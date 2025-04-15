package database.doctors

import database.DatabaseFactory
import database.doctors.Doctors.doctorEmail
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object DoctorsRepository {

    suspend fun getAllDoctorsWithSpecialization(): List<DoctorWithSpecializationDTO> {
        val result = mutableListOf<DoctorWithSpecializationDTO>()

        DatabaseFactory.dbQuery {
            transaction {
                exec(
                    """
            SELECT 
                d.doctor_id,
                d.doctor_name,
                d.doctor_surname,
                d.doctor_lastname,
                d.doctor_date_of_birth,
                d.doctor_phone_number,
                d.doctor_email,
                s.specialization_title,
                d.doctor_rate,
                d.doctor_start_work_date,
                d.doctor_password
            FROM doctors d
            JOIN doctors_specialization s 
              ON d.doctor_specialization = s.specialization_id
            """.trimIndent()
                ) { rs ->
                    while (rs.next()) {
                        result.add(
                            DoctorWithSpecializationDTO(
                                doctorId = rs.getInt("doctor_id"),
                                name = rs.getString("doctor_name"),
                                surname = rs.getString("doctor_surname"),
                                lastname = rs.getString("doctor_lastname"),
                                dateOfBirth = rs.getDate("doctor_date_of_birth").toString(),
                                phoneNumber = rs.getString("doctor_phone_number"),
                                email = rs.getString("doctor_email"),
                                specialization = rs.getString("specialization_title"),
                                rate = rs.getDouble("doctor_rate"),
                                startWorkDate = rs.getDate("doctor_start_work_date").toString(),
                                doctorPassword = rs.getString("doctor_password")
                            )
                        )
                    }
                }
            }
        }
        return result

    }

    fun fetchDoctor(login: String): DoctorWithSpecializationDTO? {
        return try {
            transaction {
                val doctorModel = Doctors.selectAll().where { doctorEmail eq login }.single()

                DoctorWithSpecializationDTO(
                    doctorId = doctorModel[Doctors.doctorId],
                    name = doctorModel[Doctors.doctorName],
                    surname = doctorModel[Doctors.doctorSurname],
                    lastname = doctorModel[Doctors.doctorLastname],
                    dateOfBirth = doctorModel[Doctors.doctorDateOfBirth].toString(),
                    phoneNumber = doctorModel[Doctors.doctorPhoneNumber],
                    email = doctorModel[Doctors.doctorEmail],
                    specialization = "", // можно достать название через join или отдельным запросом
                    rate = doctorModel[Doctors.doctorRate].toDouble(),
                    startWorkDate = doctorModel[Doctors.doctorStartWorkDate].toString(),
                    doctorPassword = doctorModel[Doctors.doctorPassword]
                )
            }
        } catch (e: Exception) {
            null
        }
    }


}


