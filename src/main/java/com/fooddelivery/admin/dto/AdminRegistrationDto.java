package com.fooddelivery.admin.dto;

import com.fooddelivery.admin.enums.AdminRole;
import lombok.Data;

@Data
public class AdminRegistrationDto {
    private String name;
    private String email;
    private String password;
    private String phone;
    private AdminRole adminRole;
    private boolean active = true;
}
