package com.andrew.doctor_appointment_system.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
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
public class PatientAccountService {

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
	public Patient save(Patient patient, @Valid PatientUserCreateRequest request) {
		
		User acc = registerUser(request);
		patient.setFirstname(request.getFirstname());
		patient.setLastname(request.getLastname());
		patient.setBirthdate(request.getBirthdate());
		patient.setMobileNo(request.getMobileNo());
		patient.setEmail(request.getEmail());
		patient.setUser(acc);
		
		return patientRepo.save(patient);
	}

	/**
	 * Register patient user account
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
	 * Update patient profile
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	public PatientProfileDTO update(Integer userId, @Valid PatientUserUpdateRequest request) {
		
		Patient patient = getPatientProfileDetails(userId);
		User user = patient.getUser();
		String plainPassword = null;
		
		if(request.getUsername() != null && !request.getUsername().isEmpty()) {
			user.setUsername(request.getUsername());
		}
		
		if(request.getPassword() != null && !request.getPassword().isEmpty()) {
			plainPassword = request.getPassword();
			user.setPassword(encoder.encode(request.getPassword()));
		}
		
		if(request.getFirstname() != null && !request.getFirstname().isEmpty()) {
			patient.setFirstname(request.getFirstname());
		}
		
		if(request.getLastname() != null && !request.getLastname().isEmpty()) {
			patient.setLastname(request.getLastname());
		}
		
		if(request.getBirthdate() != null) {
			patient.setBirthdate(request.getBirthdate());
		}
		
		if (request.getMobileNo() != null) {
			patient.setMobileNo(request.getMobileNo());
		}
		
		userRepo.save(user);
		patientRepo.save(patient);
		
		return PatientProfileMapper.toDTO(patient, plainPassword);
	}

	/**
	 * Get patient profile details by user id
	 * 
	 * @param userId
	 * @return
	 */
	private Patient getPatientProfileDetails(Integer userId) {
		
		Patient profile = patientRepo.findByUserId(userId);
		
		return profile;
	}

	
	/**
	 * Get patient profile by user id
	 * 
	 * @param userId
	 * @return
	 */
	public PatientProfileDTO getProfile(Integer userId) {
		
		Patient patient = getPatientProfileDetails(userId);
		
		return PatientProfileMapper.toDTO(patient, null);
	}
}
