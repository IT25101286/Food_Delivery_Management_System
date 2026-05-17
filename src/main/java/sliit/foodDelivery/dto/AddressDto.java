package sliit.foodDelivery.dto;

import lombok.Data;

@Data
public class AddressDto {
    private Long id;
    private String addressLine;
    private String city;
    private String postalCode;
}