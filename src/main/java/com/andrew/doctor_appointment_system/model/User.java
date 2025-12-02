package com.andrew.doctor_appointment_system.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.andrew.doctor_appointment_system.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
public class User extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Role role;
}
