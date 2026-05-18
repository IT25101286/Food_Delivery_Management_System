package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.Payment;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    Payment pay(Long orderId, String orderRef, String customerName, String paymentMethod);
    Optional<Payment> findById(Long id);
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findAll();
    void updateStatus(Long id, String status);
    void syncOrderStatusFromPayments();
    void deleteById(Long id);
}
