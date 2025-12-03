package com.andrew.doctor_appointment_system.util;

import java.util.List;
import java.util.stream.Collectors;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateResponse;
import com.andrew.doctor_appointment_system.model.dto.SpecializationDTO;

public class DoctorMapper {

	public static DoctorUserCreateResponse mapToResponse(Doctor doctor, String plainPassword) {
		
		DoctorUserCreateResponse dto = new DoctorUserCreateResponse();
		
		dto.setUserId(doctor.getUser().getId());
		dto.setUsername(doctor.getUser().getUsername());
		dto.setPassword(plainPassword);
		
		dto.setDoctorId(doctor.getId());
		dto.setFirstname(doctor.getFirstname());
		dto.setLastname(doctor.getLastname());
		dto.setMobileNo(doctor.getMobileNo());
		dto.setEmail(doctor.getEmail());
		
		List<SpecializationDTO> specs = doctor.getDoctorSpecializations()
				.stream()
				.map(ds -> {
					SpecializationDTO s = new SpecializationDTO();
					s.setId(ds.getSpecialization().getId());
					s.setName(ds.getSpecialization().getName());
					
					return s;
				})
				.collect(Collectors.toList());
		
		dto.setSpecialization(specs);
		
		return dto;
	}
}
