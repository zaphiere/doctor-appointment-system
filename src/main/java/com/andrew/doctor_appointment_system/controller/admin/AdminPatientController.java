package com.andrew.doctor_appointment_system.controller.admin;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.PatientUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.PatientUserUpdateRequest;
import com.andrew.doctor_appointment_system.service.admin.AdminPatientService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/patient")
public class AdminPatientController {
	
	@Autowired
	private AdminPatientService service;
	
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
		
		PatientProfileDTO patientAcc = service.save(patient, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Patient Account Created Successfuly", patientAcc),
			HttpStatus.CREATED
		);
	}
	
	/**
	 * Search patients by query
	 * 
	 * @param query
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<ApiResponse> search(
			@RequestParam(required = false) String query,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
	) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<PatientProfileDTO> patients = service.searchPatients(query, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient search results", patients),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get patient details by ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getPatientById(
			@PathVariable Integer id
		) {
		
		PatientProfileDTO patient = service.getPatientById(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient details", patient),
			HttpStatus.OK
		);
	}
	
	/**
	 * Edit patient and user account by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/{id}/edit")
	public ResponseEntity<ApiResponse> editPatientById(
		@PathVariable Integer id,
		@Valid @RequestBody  PatientUserUpdateRequest request
	) {
		
		PatientProfileDTO updatedPatient = 
				service.updatePatientById(id, request);
	
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient profile updated", updatedPatient),
			HttpStatus.OK
		);
	}
	
	/**
	 * Delete patient account by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<ApiResponse> deletePatientById(@PathVariable Integer id) {
		
		service.deletePatientById(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient account deleted successfully", null),
			HttpStatus.OK
		);
	}
	

}
