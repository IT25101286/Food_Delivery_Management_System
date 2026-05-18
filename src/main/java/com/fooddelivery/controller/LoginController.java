package com.fooddelivery.controller;

import com.fooddelivery.entity.Admin;
import com.fooddelivery.service.AdminService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        if (adminService.validateAdmin(email, password)) {
            Admin admin = adminService.findByEmail(email).get();
            session.setAttribute("adminId", admin.getId());
            session.setAttribute("adminEmail", admin.getEmail());
            return "redirect:/admin/dashboard";
        } else {
            model.addAttribute("error", "Invalid email or password");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/restaurants";
    }
}
