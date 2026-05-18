package com.fooddelivery.service.impl;

import com.fooddelivery.entity.User;
import com.fooddelivery.repository.UserRepository;
import com.fooddelivery.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repo;

    @Override public User save(User u) { return repo.save(u); }
    @Override public Optional<User> findById(Long id) { return repo.findById(id); }
    @Override public Optional<User> findByEmail(String e) { return repo.findByEmail(e); }
    @Override public List<User> findAll() { return repo.findAll(); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }
    @Override public boolean existsByEmail(String e) { return repo.existsByEmail(e); }

    @Override
    public Optional<User> authenticate(String email, String password) {
        return repo.findByEmail(email).filter(u -> u.getPassword().equals(password));
    }
}
