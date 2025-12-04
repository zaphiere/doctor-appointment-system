package com.andrew.doctor_appointment_system.model.dto;

import com.andrew.doctor_appointment_system.validation.UniqueSpecializationName;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecializationRequest {
	
	@UniqueSpecializationName
	@NotNull(
		message = "Name is required"
	)
	@Size(
		min = 5,
		max = 20,
		message = "Name must be between 5 and 20 characters"
	)
	private String name;
}
