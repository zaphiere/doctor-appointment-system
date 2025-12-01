package com.andrew.doctor_appointment_system.model;


import java.time.LocalDate;
import java.time.LocalTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "doctor_schedules")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE doctor_schedules SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class DoctorSchedule extends BaseEntity {
	
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
