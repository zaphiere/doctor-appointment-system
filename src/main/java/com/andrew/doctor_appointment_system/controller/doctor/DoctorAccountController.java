package com.andrew.doctor_appointment_system.controller.doctor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/doctor")
public class DoctorAccountController {
	
	@GetMapping("/test")
	public String doctorTest() {
		return "inside doctor";
	}
}
