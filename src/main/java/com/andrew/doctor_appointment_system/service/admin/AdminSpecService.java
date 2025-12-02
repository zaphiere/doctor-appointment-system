package com.andrew.doctor_appointment_system.service.admin;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.andrew.doctor_appointment_system.model.Specialization;
import com.andrew.doctor_appointment_system.model.dto.SpecializationListResponse;
import com.andrew.doctor_appointment_system.model.dto.SpecializationRequest;
import com.andrew.doctor_appointment_system.repository.SpecializationRepository;

@Service
public class AdminSpecService {
	
	@Autowired
	private SpecializationRepository repo;

	public Specialization save(
			Specialization specialization,
			SpecializationRequest request
	) {
		
		if(request.getName() != null && !request.getName().isBlank()) {
			specialization.setName(request.getName());
		}
		
		return repo.save(specialization);
	}

	public List<SpecializationListResponse> getAll() {
		
		return repo.findAll()
				.stream()
				.map(s -> new SpecializationListResponse(s.getId(), s.getName()))
				.toList();
	}

	public Optional<Specialization> getSpecById(int id) {
		
		return repo.findById(id);
	}

	public void delete(int id) {
		
		repo.findById(id).ifPresent(entity -> {
			entity.setDeletedAt(LocalDateTime.now());
			repo.save(entity);
		});
		
	}

}
