package com.andrew.doctor_appointment_system.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.enums.AppointmentStatus;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.AppointmentDTO;
import com.andrew.doctor_appointment_system.service.doctor.DoctorAppointmentService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

@RestController
@RequestMapping("/api/doctor/appointment")
public class DoctorAppointmentController {

	@Autowired
	AuthUserUtil authUserUtil;
	
	@Autowired
	DoctorAppointmentService service;
	
	/**
	 * Appointment search
	 * 
	 * @param name
	 * @param page
	 * @param size
	 * @return
	 */
	@GetMapping("/search")
	public ResponseEntity<ApiResponse> searchAppointments(
		@RequestParam(required = false) String name,
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
	) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		Pageable pageable = PageRequest.of(page, size);
		Page<AppointmentDTO> bookedAppointment = service.searchAppointment(
				name,	
				userId,
				pageable
			);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment search complete", bookedAppointment),
			HttpStatus.OK
		);
		
	}
	
	/**
	 * Get appointment by id for authenticated doctor
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> viewAppointment(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentDTO appointment = service.getAppointmentById(id, userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment details fetched successfully", appointment),
			HttpStatus.OK
		);
	}
	
	/**
	 * Change appointment status to FINISHED
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}/finished")
	public ResponseEntity<ApiResponse> finishAppointment(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentStatus status = AppointmentStatus.FINISHED;
		
		AppointmentDTO appointment = service.setStatus(id, userId, status);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment status finished", appointment),
			HttpStatus.OK
		);
	}
	
	/**
	 * Change appointment status to CANCELLED
	 * 
	 * @param id
	 * @return
	 */
	@PutMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse> cancelAppointment(@PathVariable int id) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		AppointmentStatus status = AppointmentStatus.CANCELLED;
		
		AppointmentDTO appointment = service.setStatus(id, userId, status);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Appointment status finished", appointment),
			HttpStatus.OK
		);
	}
	
}
