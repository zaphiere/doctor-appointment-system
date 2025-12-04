package com.andrew.doctor_appointment_system.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class DoctorProfileDTO {
	
	private int userId;
	private String username;
	private String password;
	
	private int doctorId;
	private String firstname;
	private String lastname;
	private int mobileNo;
	private String email;
	
	private List<String> specialization;
}
