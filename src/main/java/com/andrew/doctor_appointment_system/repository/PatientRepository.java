package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.andrew.doctor_appointment_system.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

	@Query(value = """
            SELECT * FROM patients p
            WHERE (:query IS NULL 
                OR LOWER(p.firstname::text) LIKE LOWER(CONCAT('%', :query, '%')) 
                OR LOWER(p.lastname::text) LIKE LOWER(CONCAT('%', :query, '%'))
            )
            AND (p.deleted_at IS NULL)
    """, nativeQuery = true)
	Page<Patient> searchPatients(
			@Param("query") String query, 
			Pageable pageable
	);
}
