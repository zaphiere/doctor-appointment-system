package com.andrew.doctor_appointment_system.model.dto;

import com.andrew.doctor_appointment_system.validation.UniqueUsername;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProfileUpdateRequest {
	
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
}
