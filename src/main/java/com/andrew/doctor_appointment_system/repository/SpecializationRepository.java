package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.Specialization;

public interface SpecializationRepository extends JpaRepository<Specialization, Integer> {

	// Check if specialization exists in DB
	boolean existsByName(String name);

}
