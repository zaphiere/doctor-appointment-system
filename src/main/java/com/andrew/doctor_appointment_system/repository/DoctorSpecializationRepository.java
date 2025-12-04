package com.andrew.doctor_appointment_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.DoctorSpecialization;
import com.andrew.doctor_appointment_system.model.Specialization;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Integer> {

	/**
	 * Find all doctor specializations by specialization
	 * 
	 * @param specialization
	 * @return
	 */
	List<DoctorSpecialization> findAllBySpecialization(Specialization specialization);
}
