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
import com.andrew.doctor_appointment_system.model.dto.PatientProfileDTO;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.MedicalRecordRepository;
import com.andrew.doctor_appointment_system.repository.PatientRepository;
import com.andrew.doctor_appointment_system.util.mapper.MedicalRecordMapper;
import com.andrew.doctor_appointment_system.util.mapper.PatientProfileMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Service
public class DoctorPatientService {
	
	@Autowired
	private PatientRepository patientRepo;
	
	@Autowired
	private DoctorRepository doctorRepo;
	
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
	public MedicalRecordDTO createRecord(
			Integer id, 
			Integer userId, 
			@Valid MedicalRecordRequest request,
			MedicalRecord record
	) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		Patient patient = patientRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Patient not found"));
		
		int age = Period.between(patient.getBirthdate(), LocalDate.now()).getYears();
		
		record.setPatient(patient);
		record.setDoctor(doctor);
		record.setAge(age);
		record.setWeight(record.getWeight());
		record.setBp(record.getBp());
		record.setDoctorNotes(record.getDoctorNotes());
		
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

}
