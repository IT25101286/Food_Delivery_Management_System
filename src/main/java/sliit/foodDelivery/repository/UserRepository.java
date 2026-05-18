package sliit.foodDelivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sliit.foodDelivery.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // READ: Get user by email
    Optional<User> findByEmail(String email);
    
    // READ: Check if email exists
    boolean existsByEmail(String email);
}