package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
    boolean existsByEmail(String email);
    Optional<User> authenticate(String email, String password);
}
