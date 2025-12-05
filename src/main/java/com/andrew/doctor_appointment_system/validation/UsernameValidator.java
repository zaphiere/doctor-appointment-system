package com.andrew.doctor_appointment_system.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andrew.doctor_appointment_system.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class UsernameValidator implements ConstraintValidator<UniqueUsername, String>{
	
	@Autowired 
	UserRepository userRepository;

	@Override
	public boolean isValid(String username, ConstraintValidatorContext context) {
		
		if(username == null) {
			return true;
		}
		
		return !userRepository.existsByUsername(username);
	}

}
