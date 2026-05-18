package com.fooddelivery.service.impl;

import com.fooddelivery.entity.Admin;
import com.fooddelivery.repository.AdminRepository;
import com.fooddelivery.service.interfaces.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repo;

    @Override public Admin save(Admin a) { return repo.save(a); }
    @Override public Optional<Admin> findById(Long id) { return repo.findById(id); }
    @Override public Optional<Admin> findByEmail(String e) { return repo.findByEmail(e); }
    @Override public List<Admin> findAll() { return repo.findAll(); }
    @Override public void deleteById(Long id) { repo.deleteById(id); }

    @Override
    public Optional<Admin> authenticate(String email, String password) {
        return repo.findByEmail(email).filter(a -> a.getPassword().equals(password));
    }
}
