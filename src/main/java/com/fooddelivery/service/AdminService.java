package com.fooddelivery.service;

import com.fooddelivery.entity.Admin;
import com.fooddelivery.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public boolean validateAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        return admin.isPresent() && admin.get().getPassword().equals(password);
    }

    public Admin createAdmin(String email, String password, String role) {
        Admin admin = new Admin();
        admin.setEmail(email);
        admin.setPassword(password);
        admin.setRole(role);
        return adminRepository.save(admin);
    }
}
