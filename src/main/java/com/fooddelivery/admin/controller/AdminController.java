package com.fooddelivery.admin.controller;

import com.fooddelivery.admin.dto.AdminRegistrationDto;
import com.fooddelivery.admin.dto.AdminUpdateDto;
import com.fooddelivery.admin.entity.Admin;
import com.fooddelivery.admin.enums.AdminRole;
import com.fooddelivery.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalAdmins", adminService.countTotal());
        model.addAttribute("activeAdmins", adminService.countActive());
        model.addAttribute("superAdminCount", adminService.countByRole(AdminRole.SUPER_ADMIN));
        model.addAttribute("adminCount", adminService.countByRole(AdminRole.ADMIN));
        model.addAttribute("moderatorCount", adminService.countByRole(AdminRole.MODERATOR));
        model.addAttribute("recentAdmins", adminService.findRecentAdmins(5));
        return "admin/dashboard";
    }

    // Read all admins.
    @GetMapping("/admins")
    public String listAdmins(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) AdminRole role,
            Model model) {
        model.addAttribute("admins", adminService.searchAdmins(query, role));
        model.addAttribute("query", query);
        model.addAttribute("selectedRole", role);
        model.addAttribute("roles", AdminRole.values());
        return "admin/admins";
    }

    @GetMapping("/admins/new")
    public String showCreateForm(Model model) {
        model.addAttribute("adminForm", new AdminRegistrationDto());
        model.addAttribute("roles", AdminRole.values());
        model.addAttribute("isEdit", false);
        return "admin/admin-form";
    }

    // Create admin.
    @PostMapping("/admins")
    public String createAdmin(@ModelAttribute("adminForm") AdminRegistrationDto dto,
                              RedirectAttributes ra) {
        try {
            adminService.createAdmin(dto);
            ra.addFlashAttribute("success", "Admin created successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/admins";
    }

    @GetMapping("/admins/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Admin admin = adminService.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
        model.addAttribute("adminForm", adminService.toUpdateDto(admin));
        model.addAttribute("adminId", id);
        model.addAttribute("roles", AdminRole.values());
        model.addAttribute("isEdit", true);
        return "admin/admin-form";
    }

    // Update admin.
    @PostMapping("/admins/{id}/edit")
    public String updateAdmin(@PathVariable Long id,
                              @ModelAttribute("adminForm") AdminUpdateDto dto,
                              RedirectAttributes ra) {
        try {
            adminService.updateAdmin(id, dto);
            ra.addFlashAttribute("success", "Admin updated successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/admins";
    }

    @PostMapping("/admins/{id}/delete")
    public String deleteAdmin(@PathVariable Long id,
                              Authentication authentication,
                              RedirectAttributes ra) {
        Admin current = adminService.findByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("Session error"));
        if (current.getId().equals(id)) {
            ra.addFlashAttribute("error", "You cannot delete your own account.");
            return "redirect:/admin/admins";
        }
        try {
            adminService.deleteAdmin(id);
            ra.addFlashAttribute("success", "Admin deleted successfully.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/admins";
    }
}
