package com.andrew.doctor_appointment_system.model.dto;

import java.util.Date;

import lombok.Data;

@Data
public class PatientProfileDTO {

	private int userId;
	private String username;
	private String password;
	
	private int patientId;
	private String firstname;
	private String lastname;
	private Date birthdate;
	private int mobileNo;
	private String email;
}
