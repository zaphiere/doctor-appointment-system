package com.andrew.doctor_appointment_system.model.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MedicalRecordUpdateRequest {
	
	private Integer weight;
	
	private Integer bp;
	
	@Size(
		min = 5,
		message = "Notes must not be less than 5 characters"
	)
	private String doctorNotes;
}
