package com.andrew.doctor_appointment_system.model.dto;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.validation.UniqueUsername;
import com.andrew.doctor_appointment_system.validation.ValidAdminRole;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AdminUserUpdateRequest {
	
	@UniqueUsername
	@Size(
		min = 5,
		max = 20,
		message = "Username must be between 5 and 20 characters"
	)
	@Pattern(
		regexp = "^[a-zA-Z0-9]+$",
		message = "Username must not contain special character"
	)
	private String username;
	
	@Size(
		min = 5,
		max = 20,
		message = "Password must be between 5 and 20 characters"
	)
	private String password;
	
	@ValidAdminRole
	private Role role;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}

	
}
