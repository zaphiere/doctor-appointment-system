package com.andrew.doctor_appointment_system.validation;

import com.andrew.doctor_appointment_system.enums.Role;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AdminRoleValidator implements ConstraintValidator<ValidAdminRole, Role> {

	@Override
	public boolean isValid(Role value, ConstraintValidatorContext context) {
		if(value == null) {
			return true;
		}
		
		return value == Role.ADMIN || value == Role.SUPERADMIN;
	}
}
