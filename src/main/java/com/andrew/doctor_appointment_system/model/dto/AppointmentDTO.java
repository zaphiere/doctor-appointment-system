package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class AppointmentDTO {

	private int appointmentId;
	private int patientId;
	private String patientName;
	private String doctorName;
	private DoctorScheduleDTO doctorSchedule;
	private LocalDate consultationDate;
	private LocalTime consultationTime;
	private String consultationDetails;
	private String status;
}
