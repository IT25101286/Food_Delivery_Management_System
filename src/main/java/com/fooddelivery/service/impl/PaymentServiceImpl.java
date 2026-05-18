package com.fooddelivery.service.impl;

import com.fooddelivery.entity.Order;
import com.fooddelivery.entity.Payment;
import com.fooddelivery.repository.OrderRepository;
import com.fooddelivery.repository.PaymentRepository;
import com.fooddelivery.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository repo;
    private final OrderRepository orderRepo;

    @Override
    public Payment pay(Long orderId, String orderRef, String customerName, String paymentMethod) {
        Order order = orderRepo.findById(orderId).orElseThrow();
        Payment p = Payment.builder()
                .paymentRef(generateRef())
                .orderId(orderId)
                .orderRef(orderRef)
                .customerName(customerName)
                .amount(order.getTotalAmount())
                .paymentMethod(paymentMethod)
                .status("PENDING")
                .paidAt(LocalDateTime.now())
                .build();
        // Order stays PENDING — admin must manually confirm via dashboard
        return repo.save(p);
    }

    @Override public Optional<Payment> findById(Long id) { return repo.findById(id); }
    @Override public List<Payment> findByOrderId(Long oid) { return repo.findByOrderIdOrderByPaidAtDesc(oid); }
    @Override public List<Payment> findAll() { return repo.findAll(); }

    @Override
    public void updateStatus(Long id, String status) {
        repo.findById(id).ifPresent(p -> {
            p.setStatus(status);
            repo.save(p);
            // Keep the linked order in sync with payment status
            orderRepo.findById(p.getOrderId()).ifPresent(o -> {
                if ("DELIVERED".equals(o.getStatus()) || "CANCELLED".equals(o.getStatus())) return;
                if ("COMPLETED".equalsIgnoreCase(status) || "CONFIRMED".equalsIgnoreCase(status)) {
                    o.setStatus("CONFIRMED");
                    orderRepo.save(o);
                }
            });
        });
    }

    @Override
    public void syncOrderStatusFromPayments() {
        for (Payment p : repo.findAll()) {
            if (!"COMPLETED".equalsIgnoreCase(p.getStatus()) && !"CONFIRMED".equalsIgnoreCase(p.getStatus())) continue;
            orderRepo.findById(p.getOrderId()).ifPresent(o -> {
                if ("PENDING".equals(o.getStatus())) {
                    o.setStatus("CONFIRMED");
                    orderRepo.save(o);
                }
            });
        }
    }

    @Override
    public void deleteById(Long id) { repo.deleteById(id); }

    private String generateRef() {
        String ref;
        do { ref = "PAY-" + String.format("%05d", new Random().nextInt(99999) + 1); }
        while (repo.existsByPaymentRef(ref));
        return ref;
    }
}
