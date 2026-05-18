package com.fooddelivery.admin.config;

import com.fooddelivery.admin.entity.Admin;
import com.fooddelivery.admin.enums.AdminRole;
import com.fooddelivery.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!adminRepository.existsByEmail("superadmin@food.com")) {
            Admin superAdmin = new Admin();
            superAdmin.setName("Super Admin");
            superAdmin.setEmail("superadmin@food.com");
            superAdmin.setPassword(passwordEncoder.encode("Admin@123"));
            superAdmin.setPhone("+10000000000");
            superAdmin.setAdminRole(AdminRole.SUPER_ADMIN);
            superAdmin.setActive(true);
            adminRepository.save(superAdmin);
            log.info("Default super admin created: superadmin@food.com");
        }
    }
}
