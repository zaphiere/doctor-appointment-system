package com.andrew.doctor_appointment_system.service.admin;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.andrew.doctor_appointment_system.enums.Role;
import com.andrew.doctor_appointment_system.model.User;
import com.andrew.doctor_appointment_system.model.dto.AdminUserCreateRequest;
import com.andrew.doctor_appointment_system.model.dto.AdminUserUpdateRequest;
import com.andrew.doctor_appointment_system.model.dto.ProfileUpdateRequest;
import com.andrew.doctor_appointment_system.repository.UserRepository;


@Service
public class AdminService {

	@Autowired
	private UserRepository repo;
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
	private static final Set<Role> ALLOWED_ROLES = Set.of(Role.ADMIN, Role.SUPERADMIN);

	@Transactional
	public User saveUser(User user, AdminUserCreateRequest request) {
		
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		
		if(request.getRole() != null) {
			if(!ALLOWED_ROLES.contains(request.getRole())) {
				throw new IllegalArgumentException("Role must be ADMIN or SUPERADMIN");
			}
			
			user.setRole(request.getRole());
		}
		
		return repo.save(user);
	}

	public Optional<User> getAdminById(int id) {
		
		return repo.findByIdAndRoleIn(id, Arrays.asList(Role.ADMIN, Role.SUPERADMIN));
	}

	@Transactional
	public void delete(int id) {
		
		repo.findById(id).ifPresent(entity -> {
			entity.setDeletedAt(LocalDateTime.now());
			repo.save(entity);
		});
	}

	public Optional<User> findByUsername(String name) {
		
		return Optional.of(repo.findByUsername(name));
	}

	public Page<User> search(String username, Pageable pageable) {
		
		return repo.findByRoleInAndUsernameContainingIgnoreCase(
			Arrays.asList(Role.ADMIN, Role.SUPERADMIN),
			username,
			pageable
		);
	}

	@Transactional
	public User update(User admin, ProfileUpdateRequest request) {
		
		admin.setUsername(request.getUsername());
		admin.setPassword(encoder.encode(request.getPassword()));
		
		return repo.save(admin);
	}

	@Transactional
	public User updateUserByAdmin(User user, AdminUserUpdateRequest request) {
		
		user.setUsername(request.getUsername());
		user.setPassword(encoder.encode(request.getPassword()));
		
		if(request.getRole() != null) {
			if(!ALLOWED_ROLES.contains(request.getRole())) {
				throw new IllegalArgumentException("Role must be ADMIN or SUPERADMIN");
			}
			
			user.setRole(request.getRole());
		}
		
		return repo.save(user);
	}

}
