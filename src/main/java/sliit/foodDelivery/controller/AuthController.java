package sliit.foodDelivery.controller;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sliit.foodDelivery.dto.AdminUserFormDto;
import sliit.foodDelivery.dto.ProfileUpdateDto;
import sliit.foodDelivery.dto.UserDto;
import sliit.foodDelivery.dto.UserRegistrationDto;
import sliit.foodDelivery.service.AdminDashboardService;
import sliit.foodDelivery.service.UserDashboardService;
import sliit.foodDelivery.service.UserService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AdminDashboardService adminDashboardService;
    private final UserDashboardService userDashboardService;

    @GetMapping({"/", "/home"})
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    // READ: Show registration form
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new UserRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    // CREATE: Register new user
    public String registerUser(@ModelAttribute("user") UserRegistrationDto dto) {
        userService.registerUser(dto);
        return "redirect:/login?success";
    }

    @GetMapping("/profile")
    // READ: Get current user profile
    public String profile(Authentication authentication, Model model) {
        UserDto user = userService.getUserByEmail(authentication.getName());
        ProfileUpdateDto profileUpdate = new ProfileUpdateDto();
        profileUpdate.setName(user.getName());
        profileUpdate.setEmail(user.getEmail());

        model.addAttribute("user", user);
        model.addAttribute("profileUpdate", profileUpdate);
        return "profile";
    }

    @PostMapping("/profile/update")
    // UPDATE: Update current user profile
    public String updateProfile(
            Authentication authentication,
            @ModelAttribute("profileUpdate") ProfileUpdateDto profileUpdateDto,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
            userService.updateCurrentUser(authentication.getName(), profileUpdateDto);
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/login?updated";
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/profile";
        }
    }

    @PostMapping("/profile/delete")
    // DELETE: Delete current user account
    public String deleteProfile(
            Authentication authentication,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes) {
        try {
            userService.deleteCurrentUser(authentication.getName());
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            return "redirect:/login?deleted";
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("errorMessage", exception.getMessage());
            return "redirect:/profile";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        boolean isAdmin = authentication.getAuthorities().stream()
                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        return isAdmin ? "redirect:/admin/dashboard" : "redirect:/user/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("stats", adminDashboardService.getStats());
        return "admin-dashboard";

    }

    // READ: Show admin users list
    @GetMapping("/admin/users")
    public String adminUsers(
            @RequestParam(value = "editId", required = false) Long editId,
            Model model,
            RedirectAttributes redirectAttributes) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("createUserForm", new AdminUserFormDto());

        if (editId != null) {
            try {
                UserDto editUser = userService.getUserById(editId);
                AdminUserFormDto editUserForm = new AdminUserFormDto();
                editUserForm.setName(editUser.getName());
                editUserForm.setEmail(editUser.getEmail());
                editUserForm.setPhone(editUser.getPhone());
                editUserForm.setRole(editUser.getRole());

                model.addAttribute("editUserId", editId);
                model.addAttribute("editUserForm", editUserForm);
            } catch (RuntimeException exception) {
                redirectAttributes.addFlashAttribute("adminError", "User not found for editing");
                return "redirect:/admin/users";
            }
        }

        return "admin-users";
    }

    @PostMapping("/admin/users/create")
    // CREATE: Create new user (admin)
    public String createUserByAdmin(
            @ModelAttribute("createUserForm") AdminUserFormDto createUserForm,
            RedirectAttributes redirectAttributes) {
        try {
            userService.createUserByAdmin(createUserForm);
            redirectAttributes.addFlashAttribute("adminSuccess", "User created successfully");
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("adminError", exception.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("/admin/users/{id}/update")
    // UPDATE: Update user (admin)
    public String updateUserByAdmin(
            @PathVariable("id") Long id,
            @ModelAttribute("editUserForm") AdminUserFormDto editUserForm,
            RedirectAttributes redirectAttributes) {
        try {
            userService.updateUserByAdmin(id, editUserForm);
            redirectAttributes.addFlashAttribute("adminSuccess", "User updated successfully");
            return "redirect:/admin/users";
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("adminError", exception.getMessage());
            return "redirect:/admin/users?editId=" + id;
        }
    }

    @PostMapping("/admin/users/{id}/delete")
    public String deleteUserByAdmin(
            @PathVariable("id") Long id,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {
        try {
            UserDto targetUser = userService.getUserById(id);
            if (targetUser.getEmail() != null && targetUser.getEmail().equals(authentication.getName())) {
                redirectAttributes.addFlashAttribute("adminError", "You cannot delete your currently logged-in account here");
                return "redirect:/admin/users";
            }

            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("adminSuccess", "User deleted successfully");
        } catch (RuntimeException exception) {
            redirectAttributes.addFlashAttribute("adminError", exception.getMessage());
        }

        return "redirect:/admin/users";
    }

    @GetMapping("/user/dashboard")
    public String userDashboard(Authentication authentication, Model model) {
        UserDto user = userService.getUserByEmail(authentication.getName());
        model.addAttribute("stats", userDashboardService.getStats(user.getId()));
        return "user-dashboard";
    }
}