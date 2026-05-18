package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.Admin;
import java.util.List;
import java.util.Optional;

public interface AdminService {
    Admin save(Admin admin);
    Optional<Admin> findById(Long id);
    Optional<Admin> findByEmail(String email);
    List<Admin> findAll();
    void deleteById(Long id);
    Optional<Admin> authenticate(String email, String password);
}
