package com.andrew.doctor_appointment_system.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.enums.AppointmentStatus;
import com.andrew.doctor_appointment_system.model.Appointment;
import com.andrew.doctor_appointment_system.model.DoctorSchedule;
import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.AppointmentCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.AppointmentDTO;
import com.andrew.doctor_appointment_system.model.dto.ConfirmAppointmentRequest;
import com.andrew.doctor_appointment_system.repository.AppointmentRepository;
import com.andrew.doctor_appointment_system.repository.DoctorScheduleRepository;
import com.andrew.doctor_appointment_system.repository.PatientRepository;
import com.andrew.doctor_appointment_system.util.mapper.ConsultationBookingMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;


@Service
public class PatientAppointmentService {
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private DoctorScheduleRepository doctorScheduleRepo;
	
	@Autowired
	private AppointmentRepository appointmentRepo;

	/**
	 * Book appointment for patient
	 * 
	 * @param appointment
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional
	public AppointmentDTO bookAppointment(Appointment appointment, Integer userId,
			@Valid AppointmentCreateRequest request) {
		
		Patient patient = getPatientProfileDetails(userId);
		DoctorSchedule schedule = doctorScheduleRepo.findById(request.getScheduleId())
				.orElseThrow(() -> new EntityNotFoundException("Doctor schedule not found"));
		
		appointment.setPatient(patient);
		appointment.setDoctor(schedule.getDoctor());
		appointment.setDoctorSchedule(schedule);
		appointment.setConsultationDetails(request.getConsultationDetails());
		appointment.setDate(schedule.getDateAvailable());
		appointment.setTime(schedule.getTimeFrom());
		appointment.setStatus(AppointmentStatus.PENDING);
		
		appointmentRepo.save(appointment);
		
		
		return ConsultationBookingMapper.toDTO(appointment);
	}

	/**
	 * Get patient profile details by user id
	 * 
	 * @param userId
	 * @return
	 */
	private Patient getPatientProfileDetails(Integer userId) {
		
		Patient profile = patientRepo.findByUserId(userId);
		
		if (profile == null) {
			throw new EntityNotFoundException("Patient profile not found");
		}
		
		
		return profile;
	}

	/**
	 * Get appointments for patient
	 * 
	 * @param userId
	 * @param pageable
	 * @return
	 */
	public Page<AppointmentDTO> getAppointments(Integer userId, Pageable pageable) {
		
		Patient patient = getPatientProfileDetails(userId);
		Page<Appointment> appointments = 
				appointmentRepo.findByPatientId(patient.getId(), pageable);
		
		
		return appointments.map(ConsultationBookingMapper::toDTO);
	}

	/**
	 * Get appointment by id for authenticated patient
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	public AppointmentDTO getAppointmentById(int id, Integer userId) {
		
		 Patient patient = getPatientProfileDetails(userId);
		 Appointment appointment = 
				 appointmentRepo.findByIdAndPatientId(id, patient.getId());
		 
		 if(appointment == null) {
			 throw new EntityNotFoundException("Appointment not found");
		 }
		 
		 
		 return ConsultationBookingMapper.toDTO(appointment);
	}

	
	/**
	 * Confirm appointment by id for authenticated patient
	 * 
	 * @param id
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional
	public AppointmentDTO confirmAppointment(
			int id, 
			Integer userId,
			ConfirmAppointmentRequest request
		) {
	
		 Patient patient = getPatientProfileDetails(userId);
		 Appointment appointment = 
				 appointmentRepo.findByIdAndPatientId(id, patient.getId());
		 DoctorSchedule schedule;
		 
		 if(appointment == null) {
			 throw new EntityNotFoundException("Appointment not found");
		 }
		 
		 if(appointment.getStatus() != AppointmentStatus.PENDING) {
			 throw new RuntimeException("Only pending appointments can be confirmed");
		 }
		 
		 schedule = doctorScheduleRepo.findById(appointment.getDoctorSchedule().getId())
					.orElseThrow(() -> new EntityNotFoundException("Doctor schedule not found"));
		 
		 if(request.getTime().isBefore(schedule.getTimeFrom()) || 
				 request.getTime().isAfter(schedule.getTimeTo())) {
			 
			 throw new RuntimeException(
				"Appointment time must be between " +
			 	schedule.getTimeFrom() + " and " + schedule.getTimeTo()
			 );
		 }
		 
		 appointment.setStatus(AppointmentStatus.BOOKED);
		 appointment.setTime(request.getTime());
		 appointmentRepo.save(appointment);

		 
		 return ConsultationBookingMapper.toDTO(appointment);
	}

	/**
	 * Cancel patient appointment booking by id
	 * 
	 * @param id
	 * @param userId
	 * @return
	 */
	@Transactional
	public AppointmentDTO cancelAppointment(int id, Integer userId) {
		
		Patient patient = getPatientProfileDetails(userId);
		Appointment appointment = appointmentRepo.findByIdAndPatientId(id, patient.getId());
		
		if(appointment == null) {
			throw new EntityNotFoundException("Appointment not found");
		}
		
		appointment.setStatus(AppointmentStatus.CANCELLED);
		appointmentRepo.save(appointment);
		
		
		return ConsultationBookingMapper.toDTO(appointment);
	}

}
