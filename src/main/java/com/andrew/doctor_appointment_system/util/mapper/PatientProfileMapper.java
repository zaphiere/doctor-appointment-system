package com.andrew.doctor_appointment_system.util.mapper;

import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;

public class PatientProfileMapper {
	
	public static PatientProfileDTO toDTO(Patient patient, String plainPassword) {
		
		PatientProfileDTO dto =  new PatientProfileDTO();
		
		dto.setUserId(patient.getUser().getId());
		dto.setUsername(patient.getUser().getUsername());
		dto.setPassword(plainPassword != null ? plainPassword : "********");
		
		dto.setPatientId(patient.getId());
		dto.setFirstname(patient.getFirstname());
		dto.setLastname(patient.getLastname());
		dto.setBirthdate(patient.getBirthdate());
		dto.setMobileNo(patient.getMobileNo());
		dto.setEmail(patient.getEmail());
		
		return dto;
	}
	
	public static PatientProfileDTO toDTO(Patient patient) {
        return toDTO(patient, null); // defaults password to "********"
    }

}
