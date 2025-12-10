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
import com.andrew.doctor_appointment_system.model.dto.DoctorProfileDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorSearchResultDTO;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.DoctorUserUpdateRequest;
import com.andrew.doctor_appointment_system.repository.DoctorRepository;
import com.andrew.doctor_appointment_system.repository.DoctorSpecializationRepository;
import com.andrew.doctor_appointment_system.repository.SpecializationRepository;
import com.andrew.doctor_appointment_system.repository.UserRepository;
import com.andrew.doctor_appointment_system.util.mapper.DoctorProfileMapper;
import com.andrew.doctor_appointment_system.util.mapper.DoctorSearchMapper;

import jakarta.persistence.EntityNotFoundException;
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

	/**
	 * Save doctor and register user account
	 * 
	 * @param doctor
	 * @param request
	 * @return
	 */
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

	/**
	 * Save doctor specialization
	 * 
	 * @param specializationIds
	 * @param doctor
	 */
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

	/**
	 * Register user account for doctor
	 * 
	 * @param request
	 * @return
	 */
	@Transactional
	private User registerUser(@Valid DoctorUserCreateRequest request) {
		
		User user = new User();
		
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		user.setRole(Role.DOCTOR);
		
		return userRepo.save(user);
	}

	/**
	 * Search doctors by name or specialization
	 * 
	 * @param query
	 * @param specId
	 * @param pageable
	 * @return
	 */
	public Page<DoctorSearchResultDTO> searchDoctors(String query, Integer specId, Pageable pageable) {
		
		Page<Doctor> doctors = doctorRepo.searchDoctors(query, specId, pageable);
		
		return doctors.map(DoctorSearchMapper::toDTO);
	}

	/**
	 * Get doctor profile by id
	 * 
	 * @param id
	 * @return
	 */
	public DoctorProfileDTO getDoctorById(int id) {
		
		Doctor doctor = doctorRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
		
		return DoctorProfileMapper.toDTO(doctor);
	}

	/**
	 * Update doctor and user account by id
	 * 
	 * @param id
	 * @param request
	 * @return
	 */
	@Transactional
	public DoctorProfileDTO updateDoctorById(int id, @Valid DoctorUserUpdateRequest request) {
		
		Doctor doctor = doctorRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
		User user = doctor.getUser();
		String plainPassword = null;
		
		if(request.getUsername() != null && !request.getUsername().isBlank()) {
			user.setUsername(request.getUsername());
		}
		
		if(request.getPassword() != null && !request.getPassword().isBlank()) {
			plainPassword = request.getPassword();
			user.setPassword(encoder.encode(request.getPassword()));
		}
		
		if(request.getFirstname() != null && !request.getFirstname().isBlank()) {
			doctor.setFirstname(request.getFirstname());
		}
		
		if(request.getLastname() != null && !request.getLastname().isBlank()) {
			doctor.setLastname(request.getLastname());
		}
		
		if(request.getMobileNo() != null) {
			doctor.setMobileNo(request.getMobileNo());
		}
		
		if(request.getEmail() != null && !request.getEmail().isBlank()) {
			doctor.setEmail(request.getEmail());
		}
		
		if(request.getSpecializationIds() != null) {

			doctor.getDoctorSpecializations().forEach(ds -> {
				dsRepo.delete(ds);
			});
			doctor.getDoctorSpecializations().clear();
			saveDoctorSpecialization(request.getSpecializationIds(), doctor);
		}
		
		userRepo.save(user);
		doctorRepo.save(doctor);
		
		
		return DoctorProfileMapper.toDTO(doctor, plainPassword);
	}
	/**
	 * Delete doctor and user account by id
	 * Additionally removes associated doctor specializations via cascade
	 * 
	 * @param id
	 */
	@Transactional
	public void deleteDoctor(int id) {
		
		Doctor doctor = doctorRepo.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Doctor not found"));
		
		User user = doctor.getUser();
		
		doctorRepo.delete(doctor);
		userRepo.delete(user);
	}

}
