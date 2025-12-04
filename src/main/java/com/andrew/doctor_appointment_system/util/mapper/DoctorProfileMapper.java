package com.andrew.doctor_appointment_system.util.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;

public class DoctorProfileMapper {
	
	public static DoctorProfileDTO toDTO(Doctor doctor, String plainPassword) {
		
		DoctorProfileDTO dto =  new DoctorProfileDTO();
		
		dto.setUserId(doctor.getUser().getId());
		dto.setUsername(doctor.getUser().getUsername());
		dto.setPassword(plainPassword != null ? plainPassword : "********");
		
		dto.setDoctorId(doctor.getId());
		dto.setFirstname(doctor.getFirstname());
		dto.setLastname(doctor.getLastname());
		dto.setMobileNo(doctor.getMobileNo());
		dto.setEmail(doctor.getEmail());
		
		List<String> specs = doctor.getDoctorSpecializations()
				.stream()
				.map(ds -> ds.getSpecialization().getName())
				.collect(Collectors.toList());
		dto.setSpecialization(specs);
		
		return dto;
	}
	
	public static DoctorProfileDTO toDTO(Doctor doctor) {
		
		return toDTO(doctor, null);
	}

}
