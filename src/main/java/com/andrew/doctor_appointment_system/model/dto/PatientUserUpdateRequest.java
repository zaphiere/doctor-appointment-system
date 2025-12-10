package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.andrew.doctor_appointment_system.validation.UniqueUsername;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PatientUserUpdateRequest {

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
		message = "Firstname must be between 1 and 20 characters"
	)
	private String firstname;
	
	@Size(
		min = 1,
		max = 20,
		message = "Lastname must be between 1 and 20 characters"
	)
	private String lastname;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthdate;
	
	private Integer mobileNo;
	
	@Email(
		message = "Email must be a valid email address"
	)
	private String email;
}
