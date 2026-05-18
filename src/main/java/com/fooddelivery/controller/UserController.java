package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.OrderService;
import com.fooddelivery.service.interfaces.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final OrderService orderService;
    private final PaymentService paymentService;

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        Long uid = (Long) session.getAttribute("userId");
        var myOrders = orderService.findByUserId(uid);
        model.addAttribute("orders", myOrders);
        model.addAttribute("totalOrders", myOrders.size());
        model.addAttribute("pendingOrders", myOrders.stream().filter(o -> "PENDING".equals(o.getStatus())).count());
        model.addAttribute("deliveredOrders", myOrders.stream().filter(o -> "DELIVERED".equals(o.getStatus())).count());
        return "user/dashboard";
    }
}
