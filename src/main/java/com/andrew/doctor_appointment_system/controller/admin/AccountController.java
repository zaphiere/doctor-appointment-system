package com.andrew.doctor_appointment_system.controller.admin;

import java.util.Optional;

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
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.model.dto.AdminUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.AdminUserUpdateRequest;
import com.andrew.doctor_appointment_system.model.dto.ApiResponse;
import com.andrew.doctor_appointment_system.model.dto.ProfileUpdateRequest;
import com.andrew.doctor_appointment_system.service.admin.AdminService;
import com.andrew.doctor_appointment_system.util.ApiResponseUtil;
import com.andrew.doctor_appointment_system.util.AuthUserUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin")
public class AccountController {
	
	@Autowired
	private AdminService service;
	
	@Autowired
	private AuthUserUtil authUserUtil;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(
			@Valid @RequestBody AdminUserCreateRequest request,
			User user
		) {
		
		User adminAcount = service.saveUser(user, request);

		return new ResponseEntity<>(
			ApiResponseUtil.created("Admin account successfully created", adminAcount), 
			HttpStatus.CREATED
		);
	}
	
	@GetMapping("/search")
	public ResponseEntity<ApiResponse>search(
			@RequestParam String username,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_NUMBER + "") int page,
			@RequestParam(defaultValue = AppConstant.DEFAULT_PAGE_SIZE + "") int size
	) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<User> adminList = service.search(username, pageable);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Admin Searching Complete", adminList),
			HttpStatus.OK
		);
	}
	
	
	@GetMapping("/profile")
	public ResponseEntity<ApiResponse> getProfile() {
		
		Optional<User> currentUser = authUserUtil.getCurrentAuthUser();
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Successfuly retreived current user profile", currentUser),
			HttpStatus.OK
		);
	}
	
	@PutMapping("/profile/edit")
	public ResponseEntity<ApiResponse> editProfile(@RequestBody ProfileUpdateRequest request) {
		
		Optional<User> currentUser = authUserUtil.getCurrentAuthUser();
		if(currentUser.isEmpty()) {
			return new ResponseEntity<>(
				ApiResponseUtil.unauthorized("Unauthorized"),
				HttpStatus.UNAUTHORIZED
			);
		}
		
		User updateUser = service.update(currentUser.get(), request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Successfully updated own profile", updateUser),
			HttpStatus.OK
		);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse>getAdminById(@PathVariable int id) {
		
		Optional<User> user = service.getAdminById(id);
		if(user.isEmpty()) {
			return new ResponseEntity<>(
				ApiResponseUtil.notFount("Admin Account Not Found"),
				HttpStatus.NOT_FOUND
			);
		}
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("Admin retrieved successfully", user),
			HttpStatus.OK
		);
	}
	
	@PutMapping("/{id}/edit")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<ApiResponse>editAdminById(
			@PathVariable int id, 
			@Valid @RequestBody AdminUserUpdateRequest request
	) {
		
		Optional<User> user = service.getAdminById(id);
		Optional<User> authUser = authUserUtil.getCurrentAuthUser();
		
		if(authUser.isEmpty()) {
		    return new ResponseEntity<>(
		        ApiResponseUtil.unauthorized("Unauthorized"),
		        HttpStatus.UNAUTHORIZED
		    );
		}

		if(authUser.get().getId() == id) {
			return new ResponseEntity<>(
					ApiResponseUtil.forbidden("You cannot edit your own account via this endpoint"),
					HttpStatus.FORBIDDEN
				);
		}
		
		if(user.isEmpty()) {
			return new ResponseEntity<>(
				ApiResponseUtil.notFount("Admin Account Not Found"),
				HttpStatus.NOT_FOUND
			);
		}
		User updateUser = service.updateUserByAdmin(user.get(), request);
		
		return new ResponseEntity<>(
			ApiResponseUtil.ok("User Account Updated", updateUser),
			HttpStatus.OK
		);
		
	}
	
	@DeleteMapping("/{id}/delete")
	@PreAuthorize("hasRole('SUPERADMIN')")
	public ResponseEntity<ApiResponse> delete(@PathVariable int id) {
		
		Optional<User> authUser = authUserUtil.getCurrentAuthUser();
		Optional<User> user = service.getAdminById(id);
		
		if(authUser.isEmpty()) {
			return new ResponseEntity<>(
					ApiResponseUtil.unauthorized("Unauthorized"),
					HttpStatus.UNAUTHORIZED
				);
		}
		
		if(authUser.get().getId() == id) {
			return new ResponseEntity<>(
					ApiResponseUtil.forbidden("You cannot delete your own Account"),
					HttpStatus.FORBIDDEN
				);
		}

		if(user.isEmpty()) {
			return new ResponseEntity<>(
				ApiResponseUtil.notFount("Admin account not found"),
				HttpStatus.NOT_FOUND
			);
		}
		service.delete(id);

		return new ResponseEntity<>(
			ApiResponseUtil.ok("Successfully deleted admin account", null),
			HttpStatus.OK
		);
	}
}
