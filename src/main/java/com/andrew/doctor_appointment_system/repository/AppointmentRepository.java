package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	Page<Appointment> findByPatientId(int id, Pageable pageable);

	Appointment findByIdAndPatientId(int id, int id2);

}
