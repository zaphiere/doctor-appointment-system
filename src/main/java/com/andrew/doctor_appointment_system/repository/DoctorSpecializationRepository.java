package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.DoctorSpecialization;

public interface DoctorSpecializationRepository extends JpaRepository<DoctorSpecialization, Integer> {

}
