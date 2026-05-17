package sliit.foodDelivery.dto;

import lombok.Data;
import sliit.foodDelivery.entity.Role;

@Data
public class AdminUserFormDto {
    private String name;
    private String email;
    private String phone;
    private String password;
    private Role role = Role.USER;
}
