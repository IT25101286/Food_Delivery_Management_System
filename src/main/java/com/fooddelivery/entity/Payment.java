package com.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "payments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String paymentRef;
    @Column(nullable = false)
    private Long orderId;
    @Column(nullable = false)
    private String orderRef;
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    @Column(nullable = false)
    private String paymentMethod;
    @Column(nullable = false)
    private String status;
    @Column(nullable = false)
    private LocalDateTime paidAt;
}
