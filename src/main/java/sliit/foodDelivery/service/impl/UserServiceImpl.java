package sliit.foodDelivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sliit.foodDelivery.dto.AdminUserFormDto;
import sliit.foodDelivery.dto.ProfileUpdateDto;
import sliit.foodDelivery.dto.UserDto;
import sliit.foodDelivery.dto.UserRegistrationDto;
import sliit.foodDelivery.entity.Role;
import sliit.foodDelivery.entity.User;
import sliit.foodDelivery.repository.UserRepository;
import sliit.foodDelivery.service.UserService;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // CREATE: Register new user
    @Override
    public void registerUser(UserRegistrationDto dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setPhone(dto.getPhone());
        user.setRole(Role.USER);
        userRepository.save(user);
    }

    // READ: Get user by ID
    @Override
    public UserDto getUserById(Long id) {
        return mapToDto(userRepository.findById(id).orElseThrow());
    }

    // READ: Get user by email
    @Override
    public UserDto getUserByEmail(String email) {
        return mapToDto(userRepository.findByEmail(email).orElseThrow());
    // UPDATE: Update user by ID
    }

    @Override
    public void updateUser(Long id, UserRegistrationDto dto) {
        User user = userRepository.findById(id).orElseThrow();
        user.setName(dto.getName());
        user.setPhone(dto.getPhone());
        userRepository.save(user);
    }
// DELETE: Delete user by ID
    
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
// UPDATE: Update current logged-in user profile
    
    @Override
    public void updateCurrentUser(String currentEmail, ProfileUpdateDto dto) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String nextName = dto.getName() == null ? "" : dto.getName().trim();
        String nextEmail = dto.getEmail() == null ? "" : dto.getEmail().trim().toLowerCase(Locale.ROOT);
        String nextPassword = dto.getPassword() == null ? "" : dto.getPassword().trim();

        if (nextName.isBlank()) {
            throw new RuntimeException("Name is required");
        }
        if (nextEmail.isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (!nextEmail.equals(user.getEmail()) && userRepository.existsByEmail(nextEmail)) {
            throw new RuntimeException("Email already in use");
        }
        if (!nextPassword.isBlank() && nextPassword.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        user.setName(nextName);
        user.setEmail(nextEmail);
        if (!nextPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(nextPassword));
        }
        userRepository.save(user);
    // DELETE: Delete current logged-in user
    }

    @Override
    public void deleteCurrentUser(String currentEmail) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.deleteById(user.getId());
    // CREATE: Create user from admin form
    }

    @Override
    public void createUserByAdmin(AdminUserFormDto dto) {
        String name = dto.getName() == null ? "" : dto.getName().trim();
        String email = dto.getEmail() == null ? "" : dto.getEmail().trim().toLowerCase(Locale.ROOT);
        String phone = dto.getPhone() == null ? null : dto.getPhone().trim();
        String password = dto.getPassword() == null ? "" : dto.getPassword().trim();
        Role role = dto.getRole() == null ? Role.USER : dto.getRole();

        if (name.isBlank()) {
            throw new RuntimeException("Name is required");
        }
        if (email.isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (password.isBlank() || password.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(phone);
        user.setRole(role);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    // UPDATE: Update user from admin form
    }

    @Override
    public void updateUserByAdmin(Long id, AdminUserFormDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String nextName = dto.getName() == null ? "" : dto.getName().trim();
        String nextEmail = dto.getEmail() == null ? "" : dto.getEmail().trim().toLowerCase(Locale.ROOT);
        String nextPhone = dto.getPhone() == null ? null : dto.getPhone().trim();
        String nextPassword = dto.getPassword() == null ? "" : dto.getPassword().trim();
        Role nextRole = dto.getRole() == null ? Role.USER : dto.getRole();

        if (nextName.isBlank()) {
            throw new RuntimeException("Name is required");
        }
        if (nextEmail.isBlank()) {
            throw new RuntimeException("Email is required");
        }
        if (!nextEmail.equals(user.getEmail()) && userRepository.existsByEmail(nextEmail)) {
            throw new RuntimeException("Email already in use");
        }
        if (!nextPassword.isBlank() && nextPassword.length() < 6) {
            throw new RuntimeException("Password must be at least 6 characters");
        }

        user.setName(nextName);
        user.setEmail(nextEmail);
        user.setPhone(nextPhone);
        user.setRole(nextRole);
        if (!nextPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(nextPassword));
        }

    // READ: Get all users
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private UserDto mapToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        return dto;
    }
}