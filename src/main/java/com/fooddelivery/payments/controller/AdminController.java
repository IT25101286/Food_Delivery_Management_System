package com.fooddelivery.payments.controller;

import com.fooddelivery.payments.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final PaymentService paymentService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalRevenue", paymentService.getTotalRevenue());
        model.addAttribute("pendingCount", paymentService.getPendingCount());
        model.addAttribute("completedCount", paymentService.getCompletedCount());
        model.addAttribute("failedCount", paymentService.getFailedCount());
        return "admin-dashboard";
    }

    @GetMapping("/admin/payments")
    public String allPayments(Model model) {
        model.addAttribute("payments", paymentService.getAllPayments());
        return "admin-payments";
    }

    @PostMapping("/admin/payments/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status) {
        paymentService.updateStatus(id, status);
        return "redirect:/admin/payments";
    }

    @PostMapping("/admin/payments/{id}/delete")
    public String deletePayment(@PathVariable Long id) {
        paymentService.deletePayment(id);
        return "redirect:/admin/payments";
    }
}
