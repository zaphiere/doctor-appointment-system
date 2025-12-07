package com.andrew.doctor_appointment_system.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AppointmentCreateRequest {
	
	@NotNull(message = "Schedule ID is required")
	private Integer scheduleId;
	
	private String consultationDetails;
}
