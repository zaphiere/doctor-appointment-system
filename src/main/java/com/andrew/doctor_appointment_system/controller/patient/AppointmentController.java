package com.andrew.doctor_appointment_system.controller.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.model.Appointment;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.AppointmentCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.AppointmentDTO;
import com.andrew.doctor_appointment_system.model.dto.ConfirmAppointmentRequest;
import com.andrew.doctor_appointment_system.service.patient.PatientAppointmentService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/patient/appointment")
public class AppointmentController {
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	@Autowired
	PatientAppointmentService service;
	
	/**
	 * Book appointment for patient
	 * 
	 * @param request
	 * @param appointment
	 * @return
	 */
	@PostMapping("/book")
	public ResponseEntity<ApiResponse> bookAppointment(
			@Valid @RequestBody AppointmentCreateRequest request,
			Appointment appointment
		) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentDTO bookedAppointment = service.bookAppointment(
				appointment,
				userId, 
				request
			);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment booked successfully", bookedAppointment),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get appointments for patient
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<ApiResponse> getAppointments(
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
		) {
		
		Pageable pageable = PageRequest.of(page, size);
		Integer userId = authUserUtil.getCurrentAuthUserId();
		
		Page<AppointmentDTO> appointments = 
				service.getAppointments(userId, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("List of appointments fetched successfully", appointments),
			HttpStatus.OK
		);
	}
	
	/**
	 * View appointment details by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> ViewAppointment(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentDTO appointment = service.getAppointmentById(id, userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment details fetched successfully", appointment),
			HttpStatus.OK
		);
	}
	
	/**
	 * Confirm appointment by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/{id}/confirm")
	public ResponseEntity<ApiResponse> confirmAppointment(
			@PathVariable int id,
			@Valid @RequestBody  ConfirmAppointmentRequest request
		) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentDTO appointment = service.confirmAppointment(id, userId, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment confirmed successfully", appointment),
			HttpStatus.OK
		);
	}
	
	/**
	 * Cancel appointment booking by id
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("{id}/cancel")
	public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentDTO appointment = service.cancelAppointment(id, userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment cancelled successfully", appointment),
			HttpStatus.OK
		);
	}
	
	
	
	
}
