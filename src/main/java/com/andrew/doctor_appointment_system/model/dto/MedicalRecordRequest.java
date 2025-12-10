package com.andrew.doctor_appointment_system.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicalRecordRequest {
	
	@NotNull(message = "weight required")
	private int weight;
	
	@NotNull(message = "Blood pressure required")
	private int bp;
	
	@Size(
		min = 5,
		message = "Notes must be not less than 5 characters"
	)
	@NotNull(message = "doctor notes required")
	private String doctorNotes;
}
