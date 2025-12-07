package com.andrew.doctor_appointment_system.service.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.enums.AppointmentStatus;
import com.andrew.doctor_appointment_system.model.Appointment;
import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.AppointmentDTO;
import com.andrew.doctor_appointment_system.repository.AppointmentRepository;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.util.mapper.ConsultationBookingMapper;

import jakarta.persistence.EntityNotFoundException;


@Service
public class DoctorAppointmentService {
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private AppointmentRepository appointmentRepo;

	/**
	 * Appointment search 
	 * 
	 * @param name
	 * @param userId
	 * @param pageable
	 * @return
	 */
	public Page<AppointmentDTO> searchAppointment(String name, Integer userId, Pageable pageable) {
		
		Doctor doctor = getAuthDoctorProfile(userId);
		Page<Appointment> appointments = appointmentRepo.searchAppointmentsByDoctorAndPatientName(
				doctor.getId(),
				name,
				pageable
			);
	
		return appointments.map(ConsultationBookingMapper::toDTO);
	}

	
	/**
	 * Retrieve profile of authenticated doctor+-
	 * 
	 * @param userId
	 * @return
	 */
	private Doctor getAuthDoctorProfile(Integer userId) {
		
		Doctor profile = doctorRepo.findByUserId(userId);
		
		if(profile == null) {
			throw new EntityNotFoundException("Doctor profile not found");
		}
		
		return profile;
	}


	/**
	 * Get appointment by id for authenticated doctor
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public AppointmentDTO getAppointmentById(int id, Integer userId) {
		
		Doctor doctor = getAuthDoctorProfile(userId);
		
		Appointment appointment = appointmentRepo.getAppointmentByIdAndDoctorId(id, doctor.getId());
		
		if(appointment == null) {
			throw new EntityNotFoundException("Appointment not found");
		}
		
		
		return ConsultationBookingMapper.toDTO(appointment);
	}


	/**
	 * Change appointment status
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public AppointmentDTO setStatus(
			int id, 
			Integer userId,
			AppointmentStatus status
		) {
		
		Doctor doctor = getAuthDoctorProfile(userId);
		
		Appointment appointment = appointmentRepo.getAppointmentByIdAndDoctorId(id, doctor.getId());
		
		appointment.setStatus(status);
		appointmentRepo.save(appointment);
		
		
		return ConsultationBookingMapper.toDTO(appointment);
	}

}
