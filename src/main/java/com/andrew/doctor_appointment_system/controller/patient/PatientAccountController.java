package com.andrew.doctor_appointment_system.controller.patient;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patient")
public class PatientAccountController {
	
	@GetMapping("/test")
	public String patientTest() {
		return "inside patient";
	}
}
