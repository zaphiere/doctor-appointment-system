package com.andrew.doctor_appointment_system.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.model.MedicalRecord;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordDTO;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordRequest;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.service.doctor.DoctorPatientService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor/patient")
public class DoctorPatientController {
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	@Autowired
	DoctorPatientService service;
	
	/**
	 * Search patient list by query
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
			ApiResponseUtil.ok("Retrieved patient search result", patients),
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
	public ResponseEntity<ApiResponse> getPatientById(@PathVariable Integer id) {
		
		PatientProfileDTO patient = service.getPatientById(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Patient details", patient),
			HttpStatus.OK
		);
	}
	
	/**
	 * Create patient medical record
	 * 
	 * @param id
	 * @param request
	 * @param record
	 * @return
	 */
	@PostMapping("/{id}/records/add")
	public ResponseEntity<ApiResponse> addPatientRecord(
		@PathVariable Integer id,
		@Valid @RequestBody MedicalRecordRequest request,
		MedicalRecord record
	){
		Integer userId = authUserUtil.getCurrentAuthUserId();
		MedicalRecordDTO createRecord = service.createRecord(id, userId, request, record);
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Patient medical record added", createRecord),
			HttpStatus.OK
		);
	}

}
