package com.andrew.doctor_appointment_system.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	// Find account via user name
	User findByUsername(String username);
	
	// Retrieve Admin account based on id
	Optional<User> findByIdAndRoleIn(int id, List<Role> roles);

	// Search And Retrieve Admin List
	Page<User> findByRoleInAndUsernameContainingIgnoreCase(
			List<Role> asList, 
			String username, 
			Pageable pageable
	);

	// Check if user name exists in DB
	boolean existsByUsername(String username);
}
