package com.andrew.doctor_appointment_system.controller.doctor;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.andrew.doctor_appointment_system.model.DoctorSchedule;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleRequest;
import com.andrew.doctor_appointment_system.model.dto.DoctorScheduleUpdateRequest;
import com.andrew.doctor_appointment_system.service.doctor.DoctorScheduleService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor/schedule")
public class DoctorScheduleController {
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	@Autowired
	DoctorScheduleService service;
	
	/**
	 * Create doctor schedule
	 * 
	 * @param request
	 * @param doctorSchedule
	 * @return
	 */
	@PostMapping("/create")
	public ResponseEntity<ApiResponse> createSchedule(
			@Valid @RequestBody DoctorScheduleRequest request,
			DoctorSchedule doctorSchedule
		) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		DoctorScheduleDTO schedule = service.createDoctorSchedule(
				doctorSchedule,
				userId, 
				request
			);
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Doctor schedule created", schedule),
			HttpStatus.CREATED
		);
	}
	
	/**
	 * Get doctor schedules with optional date filters
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<ApiResponse> getDoctorSchedules(
			@RequestParam(required = false) LocalDate dateFrom,
			@RequestParam(required = false) LocalDate dateTo,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
		) {
		
		Pageable pageable = PageRequest.of(page, size);
		Integer userId = authUserUtil.getCurrentAuthUserId();
		
		Page<DoctorScheduleDTO> schedules = 
				service.getDoctorSchedulesByUserId(
						userId, 
						dateFrom, 
						dateTo, 
						pageable
					);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok(
				"Doctor schedules fetched successfully", 
				schedules
			),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get doctor schedule by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getScheduleById(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		DoctorScheduleDTO schedule = service.getDoctorScheduleById(id, userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok(
				"Doctor schedule fetched successfully", 
				schedule
			),
			HttpStatus.OK
		);
	}
	
	/**
	 * Edit doctor schedule by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/{id}/edit")
	public ResponseEntity<ApiResponse> editScheduleById(
		@PathVariable int id,
		@Valid @RequestBody  DoctorScheduleUpdateRequest request
	) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		DoctorScheduleDTO updatedSchedule = 
				service.updateDoctorScheduleById(id, userId, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor schedule updated", updatedSchedule),
			HttpStatus.OK
		);
	}
	
	/**
	 * Delete doctor schedule by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<ApiResponse> deleteScheduleById(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		service.deleteDoctorScheduleById(id, userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor schedule deleted", null),
			HttpStatus.OK
		);
	}
	
	
	
	

}
