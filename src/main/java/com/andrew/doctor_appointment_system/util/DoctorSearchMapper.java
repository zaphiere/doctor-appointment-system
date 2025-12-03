package com.andrew.doctor_appointment_system.util;

import java.util.List;
import java.util.stream.Collectors;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;

public class DoctorSearchMapper {
	
	public static DoctorSearchResultDTO toDTO(Doctor doctor) {
		
		DoctorSearchResultDTO dto = new DoctorSearchResultDTO();
		
		dto.setDoctorId(doctor.getId());
		dto.setFullname(doctor.getFirstname() + " " + doctor.getLastname());
		dto.setEmail(doctor.getEmail());
		dto.setMobileNo(doctor.getMobileNo());
		
		List<String> specs = doctor.getDoctorSpecializations()
				.stream()
				.map(ds -> ds.getSpecialization().getName())
				.collect(Collectors.toList());
		
		dto.setSpecializations(specs);
		
		return dto;
	}
}
