package com.andrew.doctor_appointment_system.controller.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserUpdateRequest;
import com.andrew.doctor_appointment_system.service.doctor.DoctorService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAccountController {
	
	@Autowired
	private DoctorService service;
	
	@Autowired
	AuthUserUtil authUserUtil;
	
	@GetMapping("/test")
	public String doctorTest() {
		return "inside doctor";
	}
	
	/**
	 * Get authenticated doctor's profile
	 * 
	 * @return
	 */
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse> getProfile() {

		Integer userId = authUserUtil.getCurrentAuthUserId();
		DoctorProfileDTO profile = service.getDoctorProfileByUserId(userId);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor profile fetched successfully", profile),
			HttpStatus.OK
		);
		
	}
	
	/**
	 * Edit authenticated doctor's profile
	 * 
	 * @param request
	 * @return
	 */
	@PutMapping("/profile/edit")
	public ResponseEntity<ApiResponse> editProfile(
			@Valid @RequestBody DoctorUserUpdateRequest request) {
		
		Integer userId = authUserUtil.getCurrentAuthUserId();
		DoctorProfileDTO update = service.updateDoctor(userId, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor profile updated", update),
			HttpStatus.OK
		);
	}
	
	
	
}
