package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Integer> {

}
