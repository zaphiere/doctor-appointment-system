package com.andrew.doctor_appointment_system.util.mapper;

import com.andrew.doctor_appointment_system.model.MedicalRecord;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordDTO;

public class MedicalRecordMapper {

	public static MedicalRecordDTO toDTO(MedicalRecord record) {
		
		MedicalRecordDTO dto =  new MedicalRecordDTO();
		
		dto.setRecordId(record.getId());
		dto.setPatientId(record.getPatient().getId());
		dto.setPatientName(
			record.getPatient().getFirstname() +
			" " +
			record.getPatient().getLastname()
		);
		dto.setAge(record.getAge());
		dto.setBp(record.getBp());
		dto.setWeight(record.getWeight());
		dto.setDoctorNotes(record.getDoctorNotes());
		dto.setDoctorId(record.getDoctor().getId());
		dto.setDoctorName(
			record.getDoctor().getFirstname() +
			" " +
			record.getDoctor().getLastname()
		);
		dto.setUpdatedByDoctorId(record.getUpdatedByDoctor().getId());
		dto.setUpdatedDoctorName(
			record.getUpdatedByDoctor().getFirstname() +
			" " +
			record.getUpdatedByDoctor().getLastname()
		);
		dto.setDateCreated(record.getCreatedAt());
		dto.setDateUpdated(record.getUpdatedAt());
		
		return dto;
	}
}
