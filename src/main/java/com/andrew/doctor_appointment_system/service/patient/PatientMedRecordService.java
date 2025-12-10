package com.andrew.doctor_appointment_system.service.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.MedicalRecord;
import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordDTO;
import com.andrew.doctor_appointment_system.repository.MedicalRecordRepository;
import com.andrew.doctor_appointment_system.repository.PatientRepository;
import com.andrew.doctor_appointment_system.util.mapper.MedicalRecordMapper;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PatientMedRecordService {
	
	@Autowired
	private MedicalRecordRepository medRecRepo;
	
	@Autowired
	private PatientRepository patientRepo;

	/**
	 * Retrieve patient records
	 * 
	 * @param userId
	 * @return
	 */
	public Page<MedicalRecordDTO> getPatientRecords(Integer userId, Pageable pageable) {
		
		Patient patient = getPatientProfileDetails(userId);
		Page<MedicalRecord> records = medRecRepo.getMedicalRecordByPatientId(patient.getId(), pageable);
		
		
		return records.map(MedicalRecordMapper::toDTO);
	}

	/**
	 * Retrieve patient profile via user id
	 * 
	 * @param userId
	 * @return
	 */
	private Patient getPatientProfileDetails(Integer userId) {
		
		Patient profile = patientRepo.findByUserId(userId);
		
		if(profile == null) {
			throw new EntityNotFoundException("Patient profile not found");
		}
		 
		
		return profile;
	}

	/**
	 * Retrieve patient's medical record by Id
	 * 
	 * @param userId
	 * @param id
	 * @return
	 */
	public MedicalRecordDTO getPatientRecords(Integer userId, int id) {
		
		Patient patient = getPatientProfileDetails(userId);
		MedicalRecord record = medRecRepo.getMedicalRecordByPatientIdAndId(patient.getId(), id);
		
		if(record == null) {
			throw new EntityNotFoundException("patient medical record not found");
		}
		
		
		return MedicalRecordMapper.toDTO(record);
	}

}
