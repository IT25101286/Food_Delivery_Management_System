package com.fooddelivery.repository;

import com.fooddelivery.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByOrderIdOrderByPaidAtDesc(Long orderId);
    Optional<Payment> findByOrderRef(String orderRef);
    boolean existsByPaymentRef(String paymentRef);
}
