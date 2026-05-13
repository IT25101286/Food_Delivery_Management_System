package com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    private BigDecimal deliveryFee;

    // Restaurant management team can add more fields
    // on top of this (address, phone, openingHours etc.)
    @Column(nullable = false)
    private boolean open = true;
}