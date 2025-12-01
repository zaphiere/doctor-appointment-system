package com.andrew.doctor_appointment_system.service.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.repository.UserRepository;

@Service
public class AdminService {

	@Autowired
	private UserRepository repo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	public User saveUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		
		return repo.save(user);
	}

}
