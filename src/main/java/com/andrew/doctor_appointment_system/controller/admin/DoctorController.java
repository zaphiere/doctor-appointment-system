package com.andrew.doctor_appointment_system.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateRequest;
import com.andrew.doctor_appointment_system.service.admin.AdminDoctorService;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

	private AdminDoctorService adminDoctorService;

//	@PostMapping("/register")
//	public ResponseEntity<ApiResponse> register(
////			@RequestBody DoctorUserCreateRequest request
//			Doctor doctor
//		) {
//		
//		Doctor doctor = adminDoctorService.saveDoctor(doctor);
//	}
	
	
}
