package com.andrew.doctor_appointment_system.controller.patient;

import java.time.LocalDate;

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
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.service.patient.DoctorConsultationService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;

@RestController
@RequestMapping("/api/patient/doctor")
public class DoctorConsultationController {

	@Autowired
	private DoctorConsultationService service;

	
	/**
	 * Search doctors by query and specialization id
	 * 
	 * @param query
	 * @param specId
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<ApiResponse> search(
			@RequestParam(required = false) String query,
			@RequestParam(required = false) Integer specId,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
		) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<DoctorSearchResultDTO> result = service.searchDoctors(query, specId, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctors fetched successfully", result),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get doctor details by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getDoctorDetails(@PathVariable int id) {
		
		DoctorProfileDTO doctor = service.getDoctorDetails(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor details fetched successfully", doctor),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get doctor schedules by doctor id with optional date filters
	 * 
	 * @param docId
	 * @param dateFrom
	 * @param dateTo
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/{docId}/schedule/search")
	public ResponseEntity<ApiResponse> getDoctorSchedule(
			@PathVariable int docId,
			@RequestParam(required = false) LocalDate dateFrom,
			@RequestParam(required = false) LocalDate dateTo,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
		) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<DoctorScheduleDTO> schedules = 
				service.getDoctorSchedules(docId, dateFrom, dateTo, pageable);
		

		return new ResponseEntity<>(
			ApiResponseUtil.ok(
				"Doctor availability fetched successfully",
				schedules
			),
			HttpStatus.OK
		);
	}
}
