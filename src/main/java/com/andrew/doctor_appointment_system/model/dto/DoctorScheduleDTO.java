package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class DoctorScheduleDTO {

	private int id;
	private LocalDate dateAvailable;
	private LocalTime startTime;
	private LocalTime endTime;
}
