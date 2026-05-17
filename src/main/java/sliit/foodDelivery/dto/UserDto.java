package sliit.foodDelivery.dto;

import lombok.Data;
import sliit.foodDelivery.entity.Role;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
    private String phone;
    private Role role;
}