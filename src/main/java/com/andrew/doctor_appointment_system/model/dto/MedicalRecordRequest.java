package com.andrew.doctor_appointment_system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalRecordRequest {
	
	@NotNull(message = "weight required")
	private int weight;
	
	@NotNull(message = "Blood pressure required")
	private int bp;
	
	@NotNull(message = "doctor notes required")
	private String doctorNotes;
}
