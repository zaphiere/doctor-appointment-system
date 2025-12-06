package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalTime;

import com.andrew.doctor_appointment_system.validation.ValidDoctorScheduleTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidDoctorScheduleTime
public class DoctorScheduleUpdateRequest implements DoctorSchedulteTimeRange{
	
	@NotNull(message = "Start time is required")
	private LocalTime startTime;
	
	@NotNull(message = "End time is required")
	private LocalTime endTime;
}
