package com.andrew.doctor_appointment_system.util;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.service.admin.AdminService;

@Component
public class AuthUserUtil {
	
	private final AdminService adminService;
	
	public AuthUserUtil(AdminService adminService) {
		this.adminService = adminService;
	}
	
	public Optional<User> getCurrentAuthUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if(auth == null || !auth.isAuthenticated()) {
			return Optional.empty();
		}
		
		return adminService.findByUsername(auth.getName());
	}
	
	
	public Integer getCurrentAuthUserId() {
		
		return  getCurrentAuthUser()
				.orElseThrow(() -> new RuntimeException("Unauthorized access"))
				.getId();
	}

}
