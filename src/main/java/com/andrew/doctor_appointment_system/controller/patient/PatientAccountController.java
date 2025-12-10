package com.andrew.doctor_appointment_system.controller.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.PatientUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.PatientUserUpdateRequest;
import com.andrew.doctor_appointment_system.service.patient.PatientAccountService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;
import com.andrew.doctor_appointment_system.util.mapper.PatientProfileMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientAccountController {
	
	@Autowired
	PatientAccountService service;
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	/**
	 * Register patient account
	 * 
	 * @param request
	 * @param patient
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(
			@Valid @RequestBody PatientUserCreateRequest request,
			Patient patient
		) {
		
		Patient patientAcc = service.save(patient, request);
		PatientProfileDTO response = PatientProfileMapper.toDTO(patientAcc, request.getPassword());
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Patient Account Created Successfuly", response),
			HttpStatus.CREATED
		);
	}
	
	/**
	 * Get authenticated patient's profile
	 * 
	 * @return
	 */
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse> getProfile() {

		Integer userId = authUserUtil.getCurrentAuthUserId();
		PatientProfileDTO profile = service.getProfile(userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient profile fetched successfully", profile),
			HttpStatus.OK
		);
		
	}
	
	/**
	 * Edit authenticated patient's profile
	 * 
	 * @return
	 */
	@PutMapping("/profile/edit")
	public ResponseEntity<ApiResponse> editProfile(
			@Valid @RequestBody PatientUserUpdateRequest request
		) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		PatientProfileDTO update = service.update(userId, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Edit patient profile endpoint", update),
			HttpStatus.OK
		);
	}
}
