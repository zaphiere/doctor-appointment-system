package com.andrew.doctor_appointment_system.service.doctor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Doctor;
import com.andrew.doctor_appointment_system.model.DoctorSpecialization;
import com.andrew.doctor_appointment_system.model.Specialization;
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserUpdateRequest;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.DoctorSpecializationRepository;
import com.andrew.doctor_appointment_system.repository.SpecializationRepository;
import com.andrew.doctor_appointment_system.repository.UserRepository;
import com.andrew.doctor_appointment_system.util.mapper.DoctorProfileMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private SpecializationRepository specRepo;
	
	@Autowired
	private DoctorSpecializationRepository dsRepo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	
	
	/**
	 * Get doctor profile by user id
	 * 
	 * @param userId
	 * @return
	 */
	public DoctorProfileDTO getDoctorProfileByUserId(Integer userId) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		
		return DoctorProfileMapper.toDTO(doctor, null);
	}


	/**
	 * Get doctor profile details
	 * 
	 * @param userId
	 * @return
	 */
	private Doctor getDoctorProfileDetails(Integer userId) {
		
		Doctor profile = doctorRepo.findByUserId(userId);
		
		return profile;
	}


	/**
	 * Update doctor profile
	 * 
	 * @param userId
	 * @param request
	 * @return
	 */
	@Transactional
	public DoctorProfileDTO updateDoctor(Integer userId, @Valid DoctorUserUpdateRequest request) {
		
		Doctor doctor = getDoctorProfileDetails(userId);
		User user = doctor.getUser();
		String plainPassword = null;
		
		if(request.getUsername() != null) {
			user.setUsername(request.getUsername());
		}
		
		if(request.getPassword() != null) {
			plainPassword = request.getPassword();
			user.setPassword(encoder.encode(request.getPassword()));
		}
		
		if(request.getFirstname() != null) {
			doctor.setFirstname(request.getFirstname());
		}

		if(request.getLastname() != null) {
			doctor.setLastname(request.getLastname());
		}
		
		if(request.getMobileNo() != null) {
			doctor.setMobileNo(request.getMobileNo());
		}
		
		if(request.getEmail() != null) {
			doctor.setEmail(request.getEmail());
		}
		
		if(request.getSpecializationIds() != null) {
			
			doctor.getDoctorSpecializations().forEach(ds -> {
				dsRepo.delete(ds);
			});
			doctor.getDoctorSpecializations().clear();
			saveDoctorSpecializations(doctor, request.getSpecializationIds());
		}
		
		userRepo.save(user);
		doctorRepo.save(doctor);
		
		return DoctorProfileMapper.toDTO(doctor, plainPassword);
	}

	/**
	 * Save doctor specializations
	 * 
	 * @param doctor
	 * @param specializationIds
	 */
	@Transactional
	private void saveDoctorSpecializations(Doctor doctor, List<Integer> specializationIds) {
		
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

}
