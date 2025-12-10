package com.andrew.doctor_appointment_system.controller.admin;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.model.Specialization;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.SpecializationDTO;
import com.andrew.doctor_appointment_system.model.dto.SpecializationListResponse;
import com.andrew.doctor_appointment_system.model.dto.SpecializationRequest;
import com.andrew.doctor_appointment_system.service.admin.AdminSpecService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/specialization")
public class SpecializationController {
	
	@Autowired
	private AdminSpecService service;
	
	/**
	 * Register new specialization
	 * 
	 * @param request
	 * @param specialization
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(
			@Valid @RequestBody SpecializationRequest request,
			Specialization specialization
		) {
		
		SpecializationDTO spec = service.save(specialization, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.created("New specialization added", spec),
			HttpStatus.CREATED
		);
	}
	
	/**
	 * Get list of all specialization
	 * To be used for dropdown selection
	 * 
	 * @return
	 */
	@GetMapping("/list")
	public ResponseEntity<ApiResponse> getAllSpecialization() {
		
		List<SpecializationListResponse> spec = service.getAll();
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("List of all specialization", spec),
			HttpStatus.OK
		);
	}
	
	/**
	 * Edit specialization
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@PutMapping("/{id}/edit")
	public ResponseEntity<ApiResponse> edit(
			@PathVariable int id,
			@Valid @RequestBody SpecializationRequest request
	) {
		
		Specialization spec = service.getSpecById(id);
		SpecializationDTO update = service.save(spec, request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Specialization updated", update),
			HttpStatus.OK
		);
		
	}
	
	/**
	 * Delete specialization by id
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}/delete")
	public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
		
		service.delete(id);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Successfully deleted specialization", null),
			HttpStatus.OK
		);
	}
}
