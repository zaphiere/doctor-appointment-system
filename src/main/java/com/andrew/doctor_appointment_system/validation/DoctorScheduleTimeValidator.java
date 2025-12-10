package com.andrew.doctor_appointment_system.validation;

import com.andrew.doctor_appointment_system.model.dto.DoctorSchedulteTimeRange;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DoctorScheduleTimeValidator 
	implements ConstraintValidator<ValidDoctorScheduleTime, DoctorSchedulteTimeRange> {

	@Override
	public boolean isValid(DoctorSchedulteTimeRange request, ConstraintValidatorContext context) {

	    if (request.getStartTime() == null || request.getEndTime() == null) {
	        return true;
	    }

	    boolean isValid = request.getEndTime().isAfter(request.getStartTime());

	    if (!isValid) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate(
	                "End time must be after start time"
	        ).addPropertyNode("endTime")
	         .addConstraintViolation();
	    }

	    return isValid;
	}
}
