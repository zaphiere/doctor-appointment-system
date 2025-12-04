package com.andrew.doctor_appointment_system.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity(name = "specializations")
@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE specializations SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class Specialization extends BaseEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "specialization", cascade = CascadeType.ALL)
	private Set<DoctorSpecialization> doctorSpecializations = new HashSet<>();
}
