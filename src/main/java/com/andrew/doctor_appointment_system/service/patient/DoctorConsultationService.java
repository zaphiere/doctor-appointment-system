package com.andrew.doctor_appointment_system.service.patient;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.DoctorSchedule;
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.DoctorScheduleRepository;
import com.andrew.doctor_appointment_system.util.mapper.DoctorProfileMapper;
import com.andrew.doctor_appointment_system.util.mapper.DoctorScheduleMapper;
import com.andrew.doctor_appointment_system.util.mapper.DoctorSearchMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class DoctorConsultationService {
	
	@Autowired
	DoctorRepository doctorRepo;
	
	@Autowired
	DoctorScheduleRepository doctorScheduleRepo;
	

	/**
	 * Search doctors by query and specialization id
	 * 
	 * @param query
	 * @param specId
	 * @param pageable
	 * @return
	 */
	public Page<DoctorSearchResultDTO> searchDoctors(String query, Integer specId, Pageable pageable) {
		
		Page<Doctor> doctors = doctorRepo.searchDoctors(query, specId, pageable);
		
		return doctors.map(DoctorSearchMapper::toDTO);
	}


	/**
	 * Get doctor details by id
	 * 
	 * @param id
	 * @return
	 */
	public DoctorProfileDTO getDoctorDetails(int id) {
		
		Doctor doctor = doctorRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Doctor not found"));
		
		return DoctorProfileMapper.toDTO(doctor);
	}


	/**
	 * Get doctor schedules by doctor id with optional date filters
	 * 
	 * @param docId
	 * @param dateFrom
	 * @param dateTo
	 * @param pageable
	 * @return
	 */
	public Page<DoctorScheduleDTO> getDoctorSchedules(int docId, LocalDate dateFrom, LocalDate dateTo,
			Pageable pageable) {
		
		Page<DoctorSchedule> schedule;
		if (dateFrom == null && dateTo == null) {
			schedule = doctorScheduleRepo.findByDoctorId(docId, pageable);
		} 
		
		else if (dateFrom != null && dateTo != null) { 
			 schedule = doctorScheduleRepo.findByDoctorIdAndDateAvailableBetween(
		                docId, dateFrom, dateTo, pageable
		        );
		}
		
		else if (dateFrom != null) { 
			schedule = doctorScheduleRepo.findByDoctorIdAndDateAvailableAfter(
	                docId, dateFrom, pageable
	        );
		}
		
		else { 
			schedule = doctorScheduleRepo.findByDoctorIdAndDateAvailableBefore(
	                docId, dateTo, pageable
	        );
		}
		
		if(schedule == null || schedule.isEmpty()) {
			throw new EntityNotFoundException("No doctor schedules found");
		}
			
		
		return schedule.map(DoctorScheduleMapper::toDTO);
	}

}
