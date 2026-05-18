package sliit.foodDelivery.service;

import sliit.foodDelivery.dto.UserDto;
import sliit.foodDelivery.dto.AdminUserFormDto;
import sliit.foodDelivery.dto.ProfileUpdateDto;
import sliit.foodDelivery.dto.UserRegistrationDto;

import java.util.List;

public interface UserService {
    void registerUser(UserRegistrationDto dto);
    UserDto getUserById(Long id);
    UserDto getUserByEmail(String email);
    void updateUser(Long id, UserRegistrationDto dto);
    void deleteUser(Long id);
    void updateCurrentUser(String currentEmail, ProfileUpdateDto dto);
    void deleteCurrentUser(String currentEmail);
    void createUserByAdmin(AdminUserFormDto dto);
    void updateUserByAdmin(Long id, AdminUserFormDto dto);
    List<UserDto> getAllUsers();
}