package com.andrew.doctor_appointment_system.model.dto;

import java.util.List;

import com.andrew.doctor_appointment_system.validation.UniqueUsername;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DoctorUserUpdateRequest {
	
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
	
	@Size(
		min = 1,
		max = 20,
		message = "First name must be between 1 and 50 characters"
	)
	private String firstname;
	
	@Size(
		min = 1,
		max = 50,
		message = "First name must be between 1 and 50 characters"
	)
	private String lastname;
	

	private Integer mobileNo;

	@Email (
		message = "Enter a valid email format"
	)
	private String email;
	
	private List<Integer> specializationIds;
}
