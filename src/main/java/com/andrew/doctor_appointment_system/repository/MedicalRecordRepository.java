package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

	Page<MedicalRecord> getMedicalRecordByPatientId(Integer id, Pageable pageable);

	MedicalRecord getMedicalRecordByPatientIdAndId(Integer patient_id, Integer record_id);

}
