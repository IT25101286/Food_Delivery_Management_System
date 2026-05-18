package com.fooddelivery.admin.entity;

import com.fooddelivery.admin.enums.AdminRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "admins")
@Getter
@Setter
public class Admin extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AdminRole adminRole;

    @Column(nullable = false)
    private boolean active = true;

    private LocalDateTime lastLoginAt;
}
