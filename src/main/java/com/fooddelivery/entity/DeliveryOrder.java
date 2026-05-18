package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "delivery_orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class DeliveryOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String orderRef;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String deliveryAddress;
    @Column(nullable = false)
    private String restaurantName;
    @Column(nullable = false)
    private String status;
    private Long deliveryPersonId;
    private Integer estimatedMinutes;
}
