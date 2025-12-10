package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.andrew.doctor_appointment_system.validation.ValidDoctorScheduleTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@ValidDoctorScheduleTime
public class DoctorScheduleRequest implements DoctorSchedulteTimeRange{
	
	@NotNull(message = "Date is required")
	@Future(message = "Date must be in the future")
	private LocalDate dateAvailable;
	
	@NotNull(message = "Start time is required")
	private LocalTime startTime;
	
	@NotNull(message = "End time is required")
	private LocalTime endTime;
}
