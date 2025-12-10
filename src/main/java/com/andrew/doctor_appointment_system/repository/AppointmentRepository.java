package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.andrew.doctor_appointment_system.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Page<Appointment> findByPatientId(int id, Pageable pageable);

	Appointment findByIdAndPatientId(int id, int id2);

	@Query(value = """
		    SELECT DISTINCT a.* FROM appointments a
		    LEFT JOIN patients p ON a.patient_id = p.id AND p.deleted_at IS NULL
		    WHERE a.doctor_id = :doctorId
		      AND (
		            :query IS NULL 
		            OR LOWER(p.firstname::text) LIKE LOWER(CONCAT('%', :query, '%'))
		            OR LOWER(p.lastname::text) LIKE LOWER(CONCAT('%', :query, '%'))
		          )
		      AND a.deleted_at IS NULL
		""", nativeQuery = true)
	Page<Appointment> searchAppointmentsByDoctorAndPatientName(
	        @Param("doctorId") Integer doctorId,
	        @Param("query") String query,
	        Pageable pageable
	);

	Appointment getAppointmentByIdAndDoctorId(int appId, int docId);

	void deleteAllByDoctorScheduleId(int id);
}
