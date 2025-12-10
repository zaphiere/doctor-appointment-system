package com.andrew.doctor_appointment_system.repository;

import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.DoctorSchedule;

public interface DoctorScheduleRepository extends JpaRepository<DoctorSchedule, Integer> {


	DoctorSchedule findByIdAndDoctorId(int id, Integer doctorId);

	Page<DoctorSchedule> findByDoctorId(int id, Pageable pageable);

	Page<DoctorSchedule> findByDoctorIdAndDateAvailableBetween(
			int id, 
			LocalDate dateFrom, 
			LocalDate dateTo,
			Pageable pageable
		);

	Page<DoctorSchedule> findByDoctorIdAndDateAvailableAfter(
			int id, 
			LocalDate 
			dateFrom, 
			Pageable pageable
		);

	Page<DoctorSchedule> findByDoctorIdAndDateAvailableBefore(
			int id,
			LocalDate dateTo,
			Pageable pageable
		);
}
