package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "restaurants")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Restaurant {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String address;
    private String phone;
    private String cuisineType;
    @Column(nullable = false)
    private String status;
    private String openingTime;
    private String closingTime;
    private String ownerName;
}
