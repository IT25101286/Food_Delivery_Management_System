package sliit.foodDelivery.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import sliit.foodDelivery.entity.Role;
import sliit.foodDelivery.entity.User;
import sliit.foodDelivery.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByEmail("admin@foodapp.com")) {
            User admin = new User();
            admin.setName("Admin User");
            admin.setEmail("admin@foodapp.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }
}