package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "delivery_persons")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DeliveryPerson {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    private String phone;
    @Column(nullable = false)
    private boolean isAvailable;
}
