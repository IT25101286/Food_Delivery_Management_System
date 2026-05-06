package com.fooddelivery.admin.service.impl;

import com.fooddelivery.admin.dto.AdminRegistrationDto;
import com.fooddelivery.admin.dto.AdminUpdateDto;
import com.fooddelivery.admin.entity.Admin;
import com.fooddelivery.admin.enums.AdminRole;
import com.fooddelivery.admin.repository.AdminRepository;
import com.fooddelivery.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Admin> findAll() {
        return adminRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    // Create admin.
    @Override
    public Admin createAdmin(AdminRegistrationDto dto) {
        if (adminRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + dto.getEmail());
        }
        Admin admin = new Admin();
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        admin.setPhone(dto.getPhone());
        admin.setAdminRole(dto.getAdminRole());
        admin.setActive(dto.isActive());
        return adminRepository.save(admin);
    }

    // Update admin.
    @Override
    public Admin updateAdmin(Long id, AdminUpdateDto dto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found with id: " + id));
        admin.setName(dto.getName());
        admin.setEmail(dto.getEmail());
        admin.setPhone(dto.getPhone());
        admin.setAdminRole(dto.getAdminRole());
        admin.setActive(dto.isActive());
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            admin.setPassword(passwordEncoder.encode(dto.getPassword()));
        }
        return adminRepository.save(admin);
    }

    // Delete admin.
    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }

    // Search admins.
    @Override
    @Transactional(readOnly = true)
    public List<Admin> searchAdmins(String query, AdminRole role) {
        return adminRepository.searchAdmins(query, role);
    }

    @Override
    @Transactional(readOnly = true)
    public long countTotal() {
        return adminRepository.count();
    }

    @Override
    @Transactional(readOnly = true)
    public long countActive() {
        return adminRepository.countByActiveTrue();
    }

    @Override
    @Transactional(readOnly = true)
    public long countByRole(AdminRole role) {
        return adminRepository.countByAdminRole(role);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> findRecentAdmins(int limit) {
        return adminRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, limit));
    }

    @Override
    public AdminUpdateDto toUpdateDto(Admin admin) {
        AdminUpdateDto dto = new AdminUpdateDto();
        dto.setName(admin.getName());
        dto.setEmail(admin.getEmail());
        dto.setPhone(admin.getPhone());
        dto.setAdminRole(admin.getAdminRole());
        dto.setActive(admin.isActive());
        return dto;
    }
}
