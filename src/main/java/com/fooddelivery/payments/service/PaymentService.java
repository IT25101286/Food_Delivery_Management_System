package com.fooddelivery.payments.service;

import com.fooddelivery.payments.entity.Payment;
import com.fooddelivery.payments.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public Payment createPayment(String orderRef, String customerName, BigDecimal amount, String paymentMethod) {
        String paymentRef = "PAY-" + java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Payment payment = Payment.builder()
                .paymentRef(paymentRef)
                .orderRef(orderRef)
                .customerName(customerName)
                .amount(amount)
                .paymentMethod(paymentMethod)
                .status("PENDING")
                .paidAt(LocalDateTime.now())
                .build();

        return paymentRepository.save(payment);
    }

    public List<Payment> getCustomerPayments(String customerName) {
        return paymentRepository.findByCustomerNameOrderByPaidAtDesc(customerName);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll().stream()
                .sorted((a, b) -> b.getPaidAt().compareTo(a.getPaidAt()))
                .toList();
    }

    public BigDecimal getTotalRevenue() {
        return paymentRepository.totalCompletedRevenue();
    }

    public long getPendingCount() {
        return paymentRepository.countByStatus("PENDING");
    }

    public long getCompletedCount() {
        return paymentRepository.countByStatus("COMPLETED");
    }

    public long getFailedCount() {
        return paymentRepository.countByStatus("FAILED");
    }

    @Transactional
    public void updateStatus(Long id, String status) {
        Optional<Payment> paymentOpt = paymentRepository.findById(id);
        paymentOpt.ifPresent(payment -> {
            payment.setStatus(status);
            paymentRepository.save(payment);
        });
    }

    @Transactional
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
}
