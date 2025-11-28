package com.andrew.doctor_appointment_system.model;


import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "doctor_schedules")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorSchedule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name ="doctor_id", nullable = false)
	private Doctor doctor;
	
	@Column(nullable = false)
	private LocalDate dateAvailable;
	
	@Column(nullable = false)
	private LocalTime timeFrom;
	
	@Column(nullable = false)
	private LocalTime timeTo;
}
