package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.andrew.doctor_appointment_system.model.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {

	@Query(value = """
		    SELECT DISTINCT d.* FROM doctors d
		    LEFT JOIN doctor_specializations ds ON d.id = ds.doctor_id AND ds.deleted_at IS NULL
		    LEFT JOIN specializations s ON s.id = ds.spec_id AND s.deleted_at IS NULL
		    WHERE (:query IS NULL OR LOWER(d.firstname::text) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(d.lastname::text) LIKE LOWER(CONCAT('%', :query, '%')))
		      AND (:specId IS NULL OR s.id = :specId)
	""", nativeQuery = true)
	Page<Doctor> searchDoctors(
			@Param("query") String query, 
			@Param("specId") Integer specId, 
			Pageable pageable
	);

	Doctor findByUserId(Integer userId);

}
