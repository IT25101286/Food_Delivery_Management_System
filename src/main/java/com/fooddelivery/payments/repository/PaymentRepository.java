package com.fooddelivery.payments.repository;

import com.fooddelivery.payments.entity.Payment;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByCustomerNameOrderByPaidAtDesc(String customerName);

    long countByStatus(String status);

    @Query("select coalesce(sum(p.amount), 0) from Payment p where p.status = 'COMPLETED'")
    BigDecimal totalCompletedRevenue();
}
