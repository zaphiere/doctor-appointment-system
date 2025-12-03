package com.andrew.doctor_appointment_system.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.config.AppConstant;
import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateResponse;
import com.andrew.doctor_appointment_system.service.admin.AdminDoctorService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.DoctorMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/doctor")
public class DoctorController {

	@Autowired
	private AdminDoctorService adminDoctorService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(
			@Valid @RequestBody DoctorUserCreateRequest request,
			Doctor doctor
		) {
		
		Doctor doctorAcc = adminDoctorService.saveDoctor(doctor, request);
		DoctorUserCreateResponse response = DoctorMapper.mapToResponse(doctorAcc, request.getPassword());
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Doctor Account Created Successfuly", response),
			HttpStatus.CREATED
		);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse> search(
		@RequestParam(required = false) String query,
		@RequestParam(required = false) Integer specId,
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
		@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
	) {
		Pageable pageable = PageRequest.of(page, size);
		Page<DoctorSearchResultDTO> result = 
				adminDoctorService.searchDoctors(query, specId, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor Search Complete", result),
			HttpStatus.OK
		);
	}
	
	
}
