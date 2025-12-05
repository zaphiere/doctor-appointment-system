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
import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserUpdateRequest;
import com.andrew.doctor_appointment_system.service.admin.AdminDoctorService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.mapper.DoctorProfileMapper;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/doctor")
public class AdminDoctorController {

	@Autowired
	private AdminDoctorService adminDoctorService;

	/**
	 * Register doctor account
	 * 
	 * @param request
	 * @param doctor
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(
			@Valid @RequestBody DoctorUserCreateRequest request,
			Doctor doctor
		) {
		
		Doctor doctorAcc = adminDoctorService.saveDoctor(doctor, request);
		DoctorProfileDTO response = DoctorProfileMapper.toDTO(doctorAcc, request.getPassword());
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("Doctor Account Created Successfuly", response),
			HttpStatus.CREATED
		);
	}
	
	/**
	 * Search doctors by name or specialization
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
		Page<DoctorSearchResultDTO> result = 
				adminDoctorService.searchDoctors(query, specId, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor Search Complete", result),
			HttpStatus.OK
		);
	}
	
	/**
	 * Get doctor profile by id
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getDoctorById(@PathVariable int id) {
		
		DoctorProfileDTO doctor = 
				adminDoctorService.getDoctorById(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Retrieved doctor profile", doctor),
			HttpStatus.OK
		);
	}
	
	@PutMapping("/{id}/edit")
	public ResponseEntity<ApiResponse> editDoctorById(
		@PathVariable int id,
		@Valid @RequestBody  DoctorUserUpdateRequest request
	) {
		
		DoctorProfileDTO updatedDoctor = 
				adminDoctorService.updateDoctorById(id, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor profile updated", updatedDoctor),
			HttpStatus.OK
		);
	}
	
	/**
	 * Delete doctor account by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<ApiResponse> deleteDoctorById(@PathVariable int id) {
		
		adminDoctorService.deleteDoctor(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Doctor account deleted", null),
			HttpStatus.OK
		);
	}
	
	
}
