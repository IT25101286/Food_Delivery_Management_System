package sliit.foodDelivery.dto;

import lombok.Data;

@Data
public class ProfileUpdateDto {
    private String name;
    private String email;
    private String password;
}
