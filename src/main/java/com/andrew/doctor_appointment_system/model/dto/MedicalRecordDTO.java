package com.andrew.doctor_appointment_system.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class MedicalRecordDTO {
	
	private int recordId;
	private int patientId;
	private String patientName;
	private int age;
	private int bp;
	private int weight;
	private String doctorNotes;
	private int appointmentId;
	private int doctorId;
	private String doctorName;
	private LocalDateTime dateCreated;
}
