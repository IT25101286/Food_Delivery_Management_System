package com.fooddelivery.controller;

import com.fooddelivery.entity.Admin;
import com.fooddelivery.service.interfaces.AdminService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {
    private final AdminService adminService;

    private boolean notSuperAdmin(HttpSession s) {
        return s.getAttribute("userId") == null || !"SUPER_ADMIN".equals(s.getAttribute("userRole"));
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (notSuperAdmin(session)) return "redirect:/login";
        model.addAttribute("admins", adminService.findAll());
        return "superadmin/dashboard";
    }

    @GetMapping("/admins/new")
    public String newAdmin(HttpSession session, Model model) {
        if (notSuperAdmin(session)) return "redirect:/login";
        model.addAttribute("admin", new Admin());
        model.addAttribute("formAction", "/superadmin/admins/save");
        model.addAttribute("isNew", true);
        return "superadmin/admin-form";
    }

    @PostMapping("/admins/save")
    public String saveAdmin(@RequestParam String name, @RequestParam String email,
                            @RequestParam String password, @RequestParam(required = false) String phone,
                            @RequestParam String adminRole, HttpSession session) {
        if (notSuperAdmin(session)) return "redirect:/login";
        adminService.save(Admin.builder().name(name).email(email).password(password)
                .phone(phone).adminRole(adminRole).build());
        return "redirect:/superadmin/dashboard";
    }

    @GetMapping("/admins/{id}/edit")
    public String editAdmin(@PathVariable Long id, HttpSession session, Model model) {
        if (notSuperAdmin(session)) return "redirect:/login";
        var admin = adminService.findById(id).orElse(null);
        if (admin == null) return "redirect:/superadmin/dashboard";
        model.addAttribute("admin", admin);
        model.addAttribute("formAction", "/superadmin/admins/" + id + "/update");
        model.addAttribute("isNew", false);
        return "superadmin/admin-form";
    }

    @PostMapping("/admins/{id}/update")
    public String updateAdmin(@PathVariable Long id, @RequestParam String name,
                              @RequestParam String email, @RequestParam String password,
                              @RequestParam(required = false) String phone,
                              @RequestParam String adminRole, HttpSession session) {
        if (notSuperAdmin(session)) return "redirect:/login";
        adminService.findById(id).ifPresent(a -> {
            a.setName(name); a.setEmail(email); a.setPassword(password);
            a.setPhone(phone); a.setAdminRole(adminRole);
            adminService.save(a);
        });
        return "redirect:/superadmin/dashboard";
    }

    private static final java.util.Set<String> PROTECTED_EMAILS =
            java.util.Set.of("admin@food.com", "superadmin@food.com");

    @PostMapping("/admins/{id}/delete")
    public String deleteAdmin(@PathVariable Long id, HttpSession session) {
        if (notSuperAdmin(session)) return "redirect:/login";
        Long currentId = (Long) session.getAttribute("userId");
        if (!id.equals(currentId)) {
            adminService.findById(id).ifPresent(a -> {
                if (!PROTECTED_EMAILS.contains(a.getEmail())) {
                    adminService.deleteById(id);
                }
            });
        }
        return "redirect:/superadmin/dashboard";
    }
}
