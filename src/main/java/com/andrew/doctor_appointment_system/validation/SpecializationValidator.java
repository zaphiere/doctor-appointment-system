package com.andrew.doctor_appointment_system.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.andrew.doctor_appointment_system.repository.SpecializationRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

@Component
public class SpecializationValidator implements ConstraintValidator<UniqueSpecializationName, String > {

	@Autowired
	SpecializationRepository specRepository;
	
	@Override
	public boolean isValid(String name, ConstraintValidatorContext context) {
		
		if(name == null) {
			return true;
		}
		
		System.out.println("Validating specialization: " + name.trim());
		return !specRepository.existsByName(name.trim());
	}
	
}
