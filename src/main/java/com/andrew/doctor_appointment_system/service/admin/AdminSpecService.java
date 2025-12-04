package com.andrew.doctor_appointment_system.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Specialization;
import com.andrew.doctor_appointment_system.model.dto.SpecializationListResponse;
import com.andrew.doctor_appointment_system.model.dto.SpecializationRequest;
import com.andrew.doctor_appointment_system.repository.DoctorSpecializationRepository;
import com.andrew.doctor_appointment_system.repository.SpecializationRepository;

import jakarta.transaction.Transactional;

@Service
public class AdminSpecService {
	
	@Autowired
	private SpecializationRepository repo;
	
	@Autowired
	private DoctorSpecializationRepository dsRepo;

	/**
	 * Create or update specialization
	 * 
	 * @param specialization
	 * @param request
	 * @return
	 */
	@Transactional
	public Specialization save(
			Specialization specialization,
			SpecializationRequest request
	) {
		
		return repo.save(specialization);
	}

	/**
	 * Get all specializations
	 * 
	 * @return
	 */
	public List<SpecializationListResponse> getAll() {
		
		return repo.findAll()
				.stream()
				.map(s -> new SpecializationListResponse(s.getId(), s.getName()))
				.toList();
	}

	/**
	 * Get specialization by id
	 * 
	 * @param id
	 * @return
	 */
	public Optional<Specialization> getSpecById(int id) {
		
		return repo.findById(id);
	}

	/**
	 * Soft delete specialization and related doctor specializations
	 * 
	 * @param id
	 */
	@Transactional
	public void delete(int id) {
		
		repo.findById(id).ifPresent(entity -> {
			dsRepo.findAllBySpecialization(entity).forEach(ds -> {
				ds.setDeletedAt(LocalDateTime.now());
				dsRepo.save(ds);
			});
			
			entity.setDeletedAt(LocalDateTime.now());
			repo.save(entity);
		});
		
	}

}
