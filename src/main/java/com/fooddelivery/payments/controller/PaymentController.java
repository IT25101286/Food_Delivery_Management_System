package com.fooddelivery.payments.controller;

import com.fooddelivery.payments.entity.Payment;
import com.fooddelivery.payments.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/pay")
    public String paymentForm() {
        return "payment-pay";
    }

    @PostMapping("/payment/pay")
    public String processPayment(@RequestParam String orderRef,
                                 @RequestParam BigDecimal amount,
                                 @RequestParam String paymentMethod,
                                 HttpSession session) {
        String customerName = (String) session.getAttribute("fullName");
        paymentService.createPayment(orderRef, customerName, amount, paymentMethod);
        return "redirect:/payment/success";
    }

    @GetMapping("/payment/success")
    public String paymentSuccess() {
        return "payment-success";
    }

    @GetMapping("/payment/my-payments")
    public String myPayments(HttpSession session, Model model) {
        String customerName = (String) session.getAttribute("fullName");
        List<Payment> payments = paymentService.getCustomerPayments(customerName);
        model.addAttribute("payments", payments);
        return "payment-my-payments";
    }
}
