package com.andrew.doctor_appointment_system.service.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.DoctorSpecialization;
import com.andrew.doctor_appointment_system.model.Specialization;
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateRequest;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.DoctorSpecializationRepository;
import com.andrew.doctor_appointment_system.repository.SpecializationRepository;
import com.andrew.doctor_appointment_system.repository.UserRepository;
import com.andrew.doctor_appointment_system.util.DoctorSearchMapper;

import jakarta.validation.Valid;

@Service
public class AdminDoctorService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private SpecializationRepository specRepo;
	
	@Autowired
	private DoctorSpecializationRepository dsRepo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

	@Transactional
	public Doctor saveDoctor(Doctor doctor, @Valid DoctorUserCreateRequest request) {
		
		User acc = registerUser(request);
		doctor.setFirstname(request.getFirstname());
		doctor.setLastname(request.getLastname());
		doctor.setMobileNo(request.getMobileNo());
		doctor.setEmail(request.getEmail());
		doctor.setUser(acc);
		
		doctor = doctorRepo.save(doctor);
		
		List<Integer> specializationIds = request.getSpecializationIds();
		
		if(specializationIds != null && !specializationIds.isEmpty()) {
			saveDoctorSpecialization(specializationIds, doctor);
		}
		
		return doctor;
	}

	@Transactional
	private void saveDoctorSpecialization(List<Integer> specializationIds, Doctor doctor) {
		
		for(Integer specId : specializationIds) {
			Specialization spec = specRepo
					.findById(specId)
					.orElseThrow(() -> new RuntimeException("Specialization not found or deleted"));
			
			DoctorSpecialization ds = new DoctorSpecialization();
			ds.setDoctor(doctor);
			ds.setSpecialization(spec);
			
			dsRepo.save(ds);
			
			doctor.getDoctorSpecializations().add(ds);
		}
		
	}

	@Transactional
	private User registerUser(@Valid DoctorUserCreateRequest request) {
		
		User user = new User();
		
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(Role.DOCTOR);
		
		return userRepo.save(user);
	}

	public Page<DoctorSearchResultDTO> searchDoctors(String query, Integer specId, Pageable pageable) {
		
		Page<Doctor> doctorPage = doctorRepo.searchDoctors(query, specId, pageable);
		
		return doctorPage.map(DoctorSearchMapper::toDTO);
	}

}
