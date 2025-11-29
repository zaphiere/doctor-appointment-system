package com.andrew.doctor_appointment_system.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.service.admin.AdminService;

@RestController
@RequestMapping("/api/admin")
public class AccountController {
	
	@Autowired
	private AdminService service;

	@GetMapping("/hello")
	public String test() {
		return ("working");
	}
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return service.saveUser(user);
	}
}
