package com.andrew.doctor_appointment_system.service.doctor;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.MedicalRecord;
import com.andrew.doctor_appointment_system.model.Patient;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordDTO;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordRequest;
import com.andrew.doctor_appointment_system.model.dto.MedicalRecordUpdateRequest;
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.MedicalRecordRepository;
import com.andrew.doctor_appointment_system.repository.PatientRepository;
import com.andrew.doctor_appointment_system.util.mapper.MedicalRecordMapper;
import com.andrew.doctor_appointment_system.util.mapper.PatientProfileMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class DoctorPatientService {
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private MedicalRecordRepository medRecRepo;

	
	/**
	 * Search patients via query
	 * 
	 * @param query
	 * @param pageable
	 * @return
	 */
	public Page<PatientProfileDTO> searchPatients(String query, Pageable pageable) {
		
		Page<Patient> patients = patientRepo.searchPatients(query, pageable);

		return patients.map(PatientProfileMapper::toDTO);
	}


	/**
	 * Get patient by id
	 * 
	 * @param id
	 * @return
	 */
	public PatientProfileDTO getPatientById(Integer id) {
		
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found")); 

		return PatientProfileMapper.toDTO(patient);
	}


	/**
	 * Create Patient Medical Record
	 * 
	 * @param id
	 * @param userId
	 * @param request
	 * @param record
	 * @return
	 */
	@Transactional
	public MedicalRecordDTO createRecord(
			Integer id, 
			Integer userId, 
			@Valid MedicalRecordRequest request
	) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
		MedicalRecord record = new MedicalRecord();
		
		int age = Period.between(patient.getBirthdate(), LocalDate.now()).getYears();
		
		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setAge(age);
		record.setWeight(request.getWeight());
		record.setBp(request.getBp());
		record.setDoctorNotes(request.getDoctorNotes());
		record.setUpdatedByDoctor(doctor);
		
		medRecRepo.save(record);
		
		MedicalRecordDTO dto = MedicalRecordMapper.toDTO(record);
		
		
		return dto;
	}


	/**
	 * Get doctor profile details
	 * 
	 * @param userId
	 * @return
	 */
	private Doctor getDoctorProfileDetails(Integer userId) {
		
		Doctor profile = doctorRepo.findByUserId(userId);
		
		if(profile == null) {
			throw new EntityNotFoundException("Doctor profile not found");
		}
		
		return profile;
	}


	/**
	 * Retrieve list of patient's medical record
	 * 
	 * @param id
	 * @param pageable
	 * @return
	 */
	public Page<MedicalRecordDTO> getPatientRecords(Integer id, Pageable pageable) {
		
		Page<MedicalRecord> records = medRecRepo.getMedicalRecordByPatientId(id, pageable);
		
		if(records.isEmpty()) {
			throw new EntityNotFoundException("No patient medical records found");
		}
		
		return records.map(MedicalRecordMapper::toDTO);
	}


	/**
	 * Retrieve patient's medical record
	 * 
	 * @param patient_id
	 * @param record_id
	 * @return
	 */
	public MedicalRecordDTO getPatientRecord(Integer patient_id, Integer record_id) {
		
		MedicalRecord record = medRecRepo.getMedicalRecordByPatientIdAndId(patient_id, record_id);
		
		if(record == null) {
			throw new EntityNotFoundException("No patient medical record found");
		}
		
		return MedicalRecordMapper.toDTO(record);
	}


	/**	
	 * Updating patient's medical record
	 * 
	 * @param userId
	 * @param patient_id
	 * @param record_id
	 * @param request
	 * @return
	 */
	public MedicalRecordDTO updatePatientRecord(Integer userId, Integer patient_id, Integer record_id,
			@Valid MedicalRecordUpdateRequest request) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		MedicalRecord record = medRecRepo.getMedicalRecordByPatientIdAndId(patient_id, record_id);
		
		if(record == null) {
			throw new EntityNotFoundException("No patient medical record found");
		}
		
		if(request.getBp() != null) {
			record.setBp(request.getBp());
		}
		
		if(request.getWeight() != null) {
			record.setWeight(request.getWeight());
		}
		
		if(request.getDoctorNotes() != null && request.getDoctorNotes().isBlank()) {
			record.setDoctorNotes(request.getDoctorNotes());
		}
		
		record.setUpdatedByDoctor(doctor);
		medRecRepo.save(record);
		
		return MedicalRecordMapper.toDTO(record);
	}


	/**
	 * Delete patient medical record
	 * 
	 * @param userId
	 * @param patient_id
	 * @param record_id
	 */
	public void deletePatientRecord(Integer userId, Integer patient_id, Integer record_id) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		MedicalRecord record = medRecRepo.getMedicalRecordByPatientIdAndId(patient_id, record_id);
		
		if(record == null) {
			throw new EntityNotFoundException("No patient medical record found");
		}
		
		record.setUpdatedByDoctor(doctor);
		medRecRepo.save(record);
		medRecRepo.delete(record);
		
	}

}
