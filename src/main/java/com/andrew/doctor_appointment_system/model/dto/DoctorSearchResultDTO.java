package com.andrew.doctor_appointment_system.model.dto;

import java.util.List;

import lombok.Data;

@Data
public class DoctorSearchResultDTO {
	int doctorId;
	private String fullname;
	private String email;
	private int mobileNo;
	private List<String> specializations;
}
