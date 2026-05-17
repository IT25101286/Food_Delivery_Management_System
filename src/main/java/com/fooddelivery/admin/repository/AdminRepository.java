package com.fooddelivery.admin.repository;

import com.fooddelivery.admin.entity.Admin;
import com.fooddelivery.admin.enums.AdminRole;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByActiveTrue();

    long countByAdminRole(AdminRole role);

    List<Admin> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT a FROM Admin a WHERE " +
           "(:query IS NULL OR :query = '' OR " +
           " LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           " LOWER(a.email) LIKE LOWER(CONCAT('%', :query, '%'))) " +
           "AND (:role IS NULL OR a.adminRole = :role) " +
           "ORDER BY a.createdAt DESC")
    List<Admin> searchAdmins(@Param("query") String query, @Param("role") AdminRole role);
}
