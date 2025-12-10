package com.andrew.doctor_appointment_system.service.doctor;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.DoctorSchedule;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleRequest;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleUpdateRequest;
import com.andrew.doctor_appointment_system.repository.AppointmentRepository;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.DoctorScheduleRepository;
import com.andrew.doctor_appointment_system.util.mapper.DoctorScheduleMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class DoctorScheduleService {

	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private DoctorScheduleRepository doctorScheduleRepo;
	
	@Autowired
	private AppointmentRepository appRepo;
	
	/**
	 * Create doctor schedule
	 * 
	 * @param doctorSchedule
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional
	public DoctorScheduleDTO createDoctorSchedule(
			DoctorSchedule doctorSchedule, 
			Integer userId, 
			@Valid DoctorScheduleRequest request
		) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		
		doctorSchedule.setDoctor(doctor);
		doctorSchedule.setDateAvailable(request.getDateAvailable());
		doctorSchedule.setTimeFrom(request.getStartTime());
		doctorSchedule.setTimeTo(request.getEndTime());
		
		doctorScheduleRepo.save(doctorSchedule);
		
		DoctorScheduleDTO dto =  DoctorScheduleMapper.toDTO(doctorSchedule);
		
		return dto;
	}


	/**
	 * Get doctor schedules by user id with optional date filters
	 * 
	 * @param userId
	 * @param dateFrom
	 * @param dateTo
	 * @param pageable
	 * @return
	 */
	public Page<DoctorScheduleDTO> getDoctorSchedulesByUserId(Integer userId, LocalDate dateFrom, LocalDate dateTo,
			Pageable pageable) {
		
		Page<DoctorSchedule> schedules;
		Doctor doctor = getDoctorProfileDetails(userId);
		
		if (dateFrom == null && dateTo == null) {
			schedules = doctorScheduleRepo.findByDoctorId(doctor.getId(), pageable);
		} 
		
		else if (dateFrom != null && dateTo != null) { 
			 schedules = doctorScheduleRepo.findByDoctorIdAndDateAvailableBetween(
		                doctor.getId(), dateFrom, dateTo, pageable
		        );
		}
		
		else if (dateFrom != null) { 
			schedules = doctorScheduleRepo.findByDoctorIdAndDateAvailableAfter(
	                doctor.getId(), dateFrom, pageable
	        );
		}
		
		else { 
			schedules = doctorScheduleRepo.findByDoctorIdAndDateAvailableBefore(
	                doctor.getId(), dateTo, pageable
	        );
		}
		
		if(schedules == null || schedules.isEmpty()) {
			throw new EntityNotFoundException("No doctor schedules found");
		}
			
		
		return schedules.map(DoctorScheduleMapper::toDTO);
	}


	/**
	 * Get doctor schedule by id
	 * 
	 * @param id
	 * @return
	 */
	public DoctorScheduleDTO getDoctorScheduleById(int id, Integer userId) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		DoctorSchedule schedule = doctorScheduleRepo.findByIdAndDoctorId(id, doctor.getId());
		
		if(schedule == null) {
			throw new EntityNotFoundException("Doctor schedule not found");
		}
		
		return DoctorScheduleMapper.toDTO(schedule);
	}
	
	/**
	 * Get doctor profile details
	 * 
	 * @param userId
	 * @return
	 */
	private Doctor getDoctorProfileDetails(Integer userId) {
		
		Doctor profile = doctorRepo.findByUserId(userId);
		
		if (profile == null) {
			throw new EntityNotFoundException("Doctor profile not found");
		}
		
		return profile;
	}


	/**
	 * Update doctor schedule by id
	 * 
	 * @param id
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional
	public DoctorScheduleDTO updateDoctorScheduleById(int id, Integer userId,
			@Valid DoctorScheduleUpdateRequest request) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		DoctorSchedule schedule = doctorScheduleRepo.findByIdAndDoctorId(id, doctor.getId());
		
		if(schedule == null) {
			throw new EntityNotFoundException("Doctor schedule not found");
		}
		
		if(request.getStartTime() != null) {
			schedule.setTimeFrom(request.getStartTime());
		}
		
		if(request.getEndTime() != null) {
			schedule.setTimeTo(request.getEndTime());
		}
		
		doctorScheduleRepo.save(schedule);
		
		return DoctorScheduleMapper.toDTO(schedule);
	}


	/**
	 * Delete doctor schedule by id
	 * Additionally will also delete related appointments
	 * 
	 * @param id
	 * @param userId
	 */
	@Transactional
	public void deleteDoctorScheduleById(int id, Integer userId) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		DoctorSchedule schedule = doctorScheduleRepo.findByIdAndDoctorId(id, doctor.getId());
		
		if(schedule == null) {
			throw new EntityNotFoundException("Doctor schedule not found");
		}
		
		appRepo.deleteAllByDoctorScheduleId(schedule.getId());
		doctorScheduleRepo.delete(schedule);
	}
}
