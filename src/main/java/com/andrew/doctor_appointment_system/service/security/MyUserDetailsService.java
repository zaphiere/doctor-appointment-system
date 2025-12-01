package com.andrew.doctor_appointment_system.service.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.repository.UserRepository;
import com.andrew.doctor_appointment_system.security.UserPrincipal;

@Service
public class MyUserDetailsService implements UserDetailsService{
	
	@Autowired
	private UserRepository repo;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByUsername(username);
		
		if(user == null) {
			System.out.println("User not Found 404");
			throw new UsernameNotFoundException("User not Found 404");
		}
		
		return new UserPrincipal(user);
	}
}
