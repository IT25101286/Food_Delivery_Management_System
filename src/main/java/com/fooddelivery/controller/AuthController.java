package com.fooddelivery.controller;

import com.fooddelivery.entity.User;
import com.fooddelivery.service.interfaces.AdminService;
import com.fooddelivery.service.interfaces.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final AdminService adminService;

    @GetMapping("/login")
    public String loginPage(HttpSession session) {
        if (session.getAttribute("userId") != null)
            return "redirect:/";
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password,
            HttpSession session, Model model) {
        var admin = adminService.authenticate(email, password);
        if (admin.isPresent()) {
            session.setAttribute("userId", admin.get().getId());
            session.setAttribute("userEmail", admin.get().getEmail());
            session.setAttribute("userName", admin.get().getName());
            boolean isSuperAdmin = "SUPER_ADMIN".equals(admin.get().getAdminRole());
            session.setAttribute("userRole", isSuperAdmin ? "SUPER_ADMIN" : "ADMIN");
            return isSuperAdmin ? "redirect:/superadmin/dashboard" : "redirect:/admin/dashboard";
        }
        var user = userService.authenticate(email, password);
        if (user.isPresent()) {
            session.setAttribute("userId", user.get().getId());
            session.setAttribute("userEmail", user.get().getEmail());
            session.setAttribute("userName", user.get().getName());
            session.setAttribute("userRole", "USER");
            return "redirect:/user/dashboard";
        }
        model.addAttribute("error", "Invalid email or password");
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(HttpSession session) {
        if (session.getAttribute("userId") != null)
            return "redirect:/";
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam String email,
            @RequestParam String password, @RequestParam(required = false) String phone,
            Model model) {
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        userService.save(User.builder().name(name).email(email).password(password).phone(phone).role("USER").build());
        return "redirect:/login?registered";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/")
    public String home(HttpSession session) {
        if (session.getAttribute("userId") == null)
            return "index";
        String role = (String) session.getAttribute("userRole");
        if ("SUPER_ADMIN".equals(role)) return "redirect:/superadmin/dashboard";
        if ("ADMIN".equals(role)) return "redirect:/admin/dashboard";
        return "redirect:/user/dashboard";
    }
}
