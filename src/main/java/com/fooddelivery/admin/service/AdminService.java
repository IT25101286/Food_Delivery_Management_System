package com.fooddelivery.admin.service;

import com.fooddelivery.admin.dto.AdminRegistrationDto;
import com.fooddelivery.admin.dto.AdminUpdateDto;
import com.fooddelivery.admin.entity.Admin;
import com.fooddelivery.admin.enums.AdminRole;

import java.util.List;
import java.util.Optional;

public interface AdminService {

    List<Admin> findAll();

    Optional<Admin> findById(Long id);

    Optional<Admin> findByEmail(String email);

    Admin createAdmin(AdminRegistrationDto dto);

    Admin updateAdmin(Long id, AdminUpdateDto dto);

    void deleteAdmin(Long id);

    List<Admin> searchAdmins(String query, AdminRole role);

    long countTotal();

    long countActive();

    long countByRole(AdminRole role);

    List<Admin> findRecentAdmins(int limit);

    AdminUpdateDto toUpdateDto(Admin admin);
}
