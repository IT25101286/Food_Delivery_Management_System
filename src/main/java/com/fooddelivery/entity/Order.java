package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String orderRef;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String deliveryAddress;
    @Column(nullable = false)
    private Long restaurantId;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    @Column(columnDefinition = "TEXT")
    private String itemsSummary;
    @Column(columnDefinition = "TEXT")
    private String itemsData;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (status == null) status = "PENDING";
    }
}
