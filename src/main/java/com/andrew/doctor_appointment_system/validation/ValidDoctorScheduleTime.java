package com.andrew.doctor_appointment_system.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DoctorScheduleTimeValidator.class)
public @interface ValidDoctorScheduleTime {

	String message() default "End time must be after start time";
	
	Class<?>[] groups() default {};
	
	Class<? extends Payload>[] payload() default {};
}
