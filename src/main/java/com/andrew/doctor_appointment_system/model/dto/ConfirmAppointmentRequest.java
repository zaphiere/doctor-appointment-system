package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConfirmAppointmentRequest {
	
	@NotNull(message = "Time is required")

	private LocalTime time;
}
