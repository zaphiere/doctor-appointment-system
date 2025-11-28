package com.andrew.doctor_appointment_system.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "medical_records")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "patient_id", nullable = false)
	private Patient patient;
	
	@OneToOne
	@JoinColumn(name = "appointment_id", nullable = false)
	private Appointment appointment;
	
	@Column(nullable = false)
	private int age;
	
	@Column(nullable = false)
	private int weight;
	
	@Column(nullable = false)
	private int bp;
	
	@Lob
	@Column(nullable = false)
	private String doctorNotes;
	
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	public void prePersist() {
	    createdAt = LocalDateTime.now();
	}
}
