package com.fooddelivery.payments.config;

import com.fooddelivery.payments.entity.AppUser;
import com.fooddelivery.payments.entity.Payment;
import com.fooddelivery.payments.repository.AppUserRepository;
import com.fooddelivery.payments.repository.PaymentRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final AppUserRepository appUserRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public void run(String... args) {
        seedUsers();
        seedPayments();
    }

    private void seedUsers() {
        if (appUserRepository.count() > 0) {
            return;
        }

        AppUser admin = AppUser.builder()
                .email("admin@food.com")
                .password("admin123")
                .role("ADMIN")
                .fullName("System Admin")
                .build();

        AppUser customer = AppUser.builder()
                .email("user@food.com")
                .password("user123")
                .role("CUSTOMER")
                .fullName("Food Customer")
                .build();

        appUserRepository.saveAll(List.of(admin, customer));
    }

    private void seedPayments() {
        if (paymentRepository.count() > 0) {
            return;
        }

        List<Payment> demoPayments = List.of(
                Payment.builder()
                        .paymentRef("PAY-0001")
                        .orderRef("ORD-1001")
                        .customerName("Food Customer")
                        .amount(new BigDecimal("18.50"))
                        .paymentMethod("CASH")
                        .status("COMPLETED")
                        .paidAt(LocalDateTime.now().minusDays(2))
                        .build(),
                Payment.builder()
                        .paymentRef("PAY-0002")
                        .orderRef("ORD-1002")
                        .customerName("Food Customer")
                        .amount(new BigDecimal("42.00"))
                        .paymentMethod("CARD")
                        .status("PENDING")
                        .paidAt(LocalDateTime.now().minusDays(1).minusHours(3))
                        .build(),
                Payment.builder()
                        .paymentRef("PAY-0003")
                        .orderRef("ORD-1003")
                        .customerName("Food Customer")
                        .amount(new BigDecimal("25.75"))
                        .paymentMethod("MOBILE")
                        .status("FAILED")
                        .paidAt(LocalDateTime.now().minusHours(20))
                        .build(),
                Payment.builder()
                        .paymentRef("PAY-0004")
                        .orderRef("ORD-1004")
                        .customerName("Food Customer")
                        .amount(new BigDecimal("63.10"))
                        .paymentMethod("CARD")
                        .status("COMPLETED")
                        .paidAt(LocalDateTime.now().minusHours(8))
                        .build(),
                Payment.builder()
                        .paymentRef("PAY-0005")
                        .orderRef("ORD-1005")
                        .customerName("Food Customer")
                        .amount(new BigDecimal("12.30"))
                        .paymentMethod("MOBILE")
                        .status("PENDING")
                        .paidAt(LocalDateTime.now().minusHours(2))
                        .build()
        );

        paymentRepository.saveAll(demoPayments);
    }
}
