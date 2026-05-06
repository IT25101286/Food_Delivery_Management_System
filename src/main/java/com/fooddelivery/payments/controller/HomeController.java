package com.fooddelivery.payments.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(HttpSession session) {
        Object roleObj = session.getAttribute("role");
        if (roleObj == null) {
            return "redirect:/login";
        }

        String role = String.valueOf(roleObj);
        if ("ADMIN".equalsIgnoreCase(role)) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/payment/pay";
    }
}
