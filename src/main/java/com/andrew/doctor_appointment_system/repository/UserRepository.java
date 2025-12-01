package com.andrew.doctor_appointment_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.andrew.doctor_appointment_system.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByUsername(String username);
}
