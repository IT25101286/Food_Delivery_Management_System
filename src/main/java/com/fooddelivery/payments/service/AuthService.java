package com.fooddelivery.payments.service;

import com.fooddelivery.payments.entity.AppUser;
import com.fooddelivery.payments.repository.AppUserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;

    public Optional<AppUser> authenticate(String email, String password) {
        return appUserRepository.findByEmail(email)
                .filter(user -> user.getPassword().equals(password));
    }
}
