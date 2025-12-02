package com.andrew.doctor_appointment_system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecializationListResponse {
	
	private int id;
	private String name;
}
