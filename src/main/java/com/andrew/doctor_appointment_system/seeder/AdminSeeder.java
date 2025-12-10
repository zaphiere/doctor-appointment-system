package com.andrew.doctor_appointment_system.seeder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.repository.UserRepository;

@Component
public class AdminSeeder implements CommandLineRunner{
	
	@Autowired
	private UserRepository repo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	@Override
	public void run(String... args) throws Exception {
		
		if(!repo.existsByUsername("admin")) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("password1234"));
			admin.setRole(Role.SUPERADMIN);
			
			repo.save(admin);
			System.out.println("Admin account seeded successfully");
		} else {
			System.out.println("Admin account already exists. skipping seeding");
		}
		
	}
	
	
}
