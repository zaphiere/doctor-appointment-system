package com.andrew.doctor_appointment_system.controller.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordDTO;
import com.andrew.doctor_appointment_system.service.patient.PatientMedRecordService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

@RestController
@RequestMapping("/api/patient/record")
public class PatientMedicalRecordController {
	
	@Autowired
	private PatientMedRecordService service;
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	
	
	/**
	 * Retrieve Patient Medical Records
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<ApiResponse> list(
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Integer userId = authUserUtil.getCurrentAuthUserId();
		
		Page<MedicalRecordDTO> records = service.getPatientRecords(userId, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Retrieved patient records", records),
			HttpStatus.OK
		);
	}
	
	/**
	 * View Patient's Medical Record
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("{id}")
	public ResponseEntity<ApiResponse> viewRecord(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		
		MedicalRecordDTO record = service.getPatientRecords(userId, id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Retrieved patient record", record),
			HttpStatus.OK
		);
	}
}
