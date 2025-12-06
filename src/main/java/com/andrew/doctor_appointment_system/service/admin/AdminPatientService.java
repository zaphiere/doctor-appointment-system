package com.andrew.doctor_appointment_system.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.PatientUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.PatientUserUpdateRequest;
import com.andrew.doctor_appointment_system.repository.PatientRepository;
import com.andrew.doctor_appointment_system.repository.UserRepository;
import com.andrew.doctor_appointment_system.util.mapper.PatientProfileMapper;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class AdminPatientService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private PatientRepository patientRepo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	/**
	 * Save patient and register user account
	 * 
	 * @param patient
	 * @param request
	 * @return
	 */
	@Transactional
	public PatientProfileDTO save(Patient patient, @Valid PatientUserCreateRequest request) {
		
		User acc =registerUser(request);
		
		patient.setUser(acc);
		patient.setFirstname(request.getFirstname());
		patient.setLastname(request.getLastname());
		patient.setBirthdate(request.getBirthdate());
		patient.setMobileNo(request.getMobileNo());
		patient.setEmail(request.getEmail());
		
		Patient savePatient = patientRepo.save(patient);
		
		PatientProfileDTO dto = PatientProfileMapper.toDTO(savePatient, request.getPassword());
		
		return dto;
	}

	/**
	 * Register user account
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	private User registerUser(@Valid PatientUserCreateRequest request) {
		
		User user = new User();
		
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(Role.PATIENT);
		
		return userRepo.save(user);
	}

	/**
	 * Search patients by query
	 * 
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<PatientProfileDTO> searchPatients(String query, Pageable pageable) {
		
		Page<Patient> patientPage = patientRepo.searchPatients(query, pageable);
		
		return patientPage.map(PatientProfileMapper::toDTO);
	}

	/**
	 * Get patient by id
	 * 
	 * @param id
	 * @return
	 */
	public PatientProfileDTO getPatientById(Integer id) {
		
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));
		
		return PatientProfileMapper.toDTO(patient);
	}

	/**
	 * Update patient and user account by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@Transactional
	public PatientProfileDTO updatePatientById(Integer id, @Valid PatientUserUpdateRequest request) {
		
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));
		
		User user = patient.getUser();
		String plainPassword = null;
		
		if (request.getUsername() != null && !request.getUsername().isBlank()) {
			user.setUsername(request.getUsername());
		}
		
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			plainPassword = request.getPassword();
			user.setPassword(encoder.encode(request.getPassword()));
		}
		
		if (request.getFirstname() != null && !request.getFirstname().isBlank()) {
			patient.setFirstname(request.getFirstname());
		}
		
		if (request.getLastname() != null && !request.getLastname().isBlank()) {
			patient.setLastname(request.getLastname());
		}
		
		if (request.getBirthdate() != null) {
			patient.setBirthdate(request.getBirthdate());
		}
		
		if (request.getMobileNo() != null) {
			patient.setMobileNo(request.getMobileNo());
		}
		
		if (request.getEmail() != null && !request.getEmail().isBlank()) {
			patient.setEmail(request.getEmail());
		}
		
		userRepo.save(user);
		patientRepo.save(patient);
		
		return PatientProfileMapper.toDTO(patient, plainPassword);
	}

	/**
	 * Delete patient by id
	 * 
	 * @param id
	 */
	@Transactional
	public void deletePatientById(Integer id) {
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Patient not found"));
		
		User user = patient.getUser();
		
		patientRepo.delete(patient);
		userRepo.delete(user);
	}

}
