package com.fooddelivery.controller;

import com.fooddelivery.entity.*;
import com.fooddelivery.service.interfaces.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final UserService userService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final OrderService orderService;
    private final PaymentService paymentService;

    private boolean notAdmin(HttpSession s) {
        return s.getAttribute("userId") == null || !"ADMIN".equals(s.getAttribute("userRole"));
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        var counts = orderService.getStatusCounts();
        model.addAttribute("totalOrders", orderService.findAll().size());
        model.addAttribute("statusCounts", counts);
        model.addAttribute("totalUsers", userService.findAll().size());
        model.addAttribute("totalRestaurants", restaurantService.findAll().size());
        model.addAttribute("recentOrders", orderService.findAll().stream().limit(5).toList());
        return "admin/dashboard";
    }

    // === USERS ===
    @GetMapping("/users")
    public String users(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("users", userService.findAll());
        return "admin/users";
    }

    @GetMapping("/users/new")
    public String newUser(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("user", new User());
        model.addAttribute("formAction", "/admin/users/save");
        return "admin/user-form";
    }

    @PostMapping("/users/save")
    public String saveUser(@RequestParam String name, @RequestParam String email,
                           @RequestParam String password, @RequestParam(required = false) String phone,
                           @RequestParam String role, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        userService.save(User.builder().name(name).email(email).password(password).phone(phone).role(role).build());
        return "redirect:/admin/users";
    }

    @GetMapping("/users/{id}/edit")
    public String editUser(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        userService.findById(id).ifPresent(u -> model.addAttribute("user", u));
        model.addAttribute("formAction", "/admin/users/" + id + "/update");
        return "admin/user-form";
    }

    @PostMapping("/users/{id}/update")
    public String updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email,
                             @RequestParam String password, @RequestParam(required = false) String phone,
                             @RequestParam String role, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        userService.findById(id).ifPresent(u -> {
            u.setName(name); u.setEmail(email); u.setPassword(password); u.setPhone(phone); u.setRole(role);
            userService.save(u);
        });
        return "redirect:/admin/users";
    }

    @PostMapping("/users/{id}/delete")
    public String deleteUser(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        userService.deleteById(id);
        return "redirect:/admin/users";
    }

    // === RESTAURANTS ===
    @GetMapping("/restaurants")
    public String restaurants(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("restaurants", restaurantService.findAll());
        return "admin/restaurants";
    }

    @GetMapping("/restaurants/new")
    public String newRestaurant(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("restaurant", new Restaurant());
        model.addAttribute("formAction", "/admin/restaurants/save");
        return "admin/restaurant-form";
    }

    @PostMapping("/restaurants/save")
    public String saveRestaurant(@RequestParam String name, @RequestParam String address,
                                  @RequestParam String phone, @RequestParam String cuisineType,
                                  @RequestParam String status, @RequestParam String openingTime,
                                  @RequestParam String closingTime, @RequestParam String ownerName,
                                  HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        restaurantService.save(Restaurant.builder().name(name).address(address).phone(phone)
                .cuisineType(cuisineType).status(status).openingTime(openingTime)
                .closingTime(closingTime).ownerName(ownerName).build());
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String editRestaurant(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        restaurantService.findById(id).ifPresent(r -> model.addAttribute("restaurant", r));
        model.addAttribute("formAction", "/admin/restaurants/" + id + "/update");
        return "admin/restaurant-form";
    }

    @PostMapping("/restaurants/{id}/update")
    public String updateRestaurant(@PathVariable Long id, @RequestParam String name,
                                    @RequestParam String address, @RequestParam String phone,
                                    @RequestParam String cuisineType, @RequestParam String status,
                                    @RequestParam String openingTime, @RequestParam String closingTime,
                                    @RequestParam String ownerName, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        restaurantService.findById(id).ifPresent(r -> {
            r.setName(name); r.setAddress(address); r.setPhone(phone); r.setCuisineType(cuisineType);
            r.setStatus(status); r.setOpeningTime(openingTime); r.setClosingTime(closingTime); r.setOwnerName(ownerName);
            restaurantService.save(r);
        });
        return "redirect:/admin/restaurants";
    }

    @PostMapping("/restaurants/{id}/delete")
    public String deleteRestaurant(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        restaurantService.deleteById(id);
        return "redirect:/admin/restaurants";
    }

    @PostMapping("/restaurants/{id}/toggle")
    public String toggleRestaurant(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        restaurantService.findById(id).ifPresent(r -> {
            r.setStatus("OPEN".equals(r.getStatus()) ? "CLOSED" : "OPEN");
            restaurantService.save(r);
        });
        return "redirect:/admin/restaurants";
    }

    // === RESTAURANT MENU ===
    @GetMapping("/restaurants/{id}/menu")
    public String restaurantMenu(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        var restaurant = restaurantService.findById(id).orElse(null);
        if (restaurant == null) return "redirect:/admin/restaurants";
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("items", menuItemService.findByRestaurantId(id));
        return "admin/restaurant-menu";
    }

    @GetMapping("/restaurants/{id}/menu/new")
    public String newRestaurantMenuItem(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        var restaurant = restaurantService.findById(id).orElse(null);
        if (restaurant == null) return "redirect:/admin/restaurants";
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("item", MenuItem.builder().restaurantId(id).build());
        model.addAttribute("formAction", "/admin/restaurants/" + id + "/menu/save");
        return "admin/restaurant-menu-form";
    }

    @PostMapping("/restaurants/{id}/menu/save")
    public String saveRestaurantMenuItem(@PathVariable Long id, @RequestParam String name,
                                          @RequestParam String category, @RequestParam BigDecimal price,
                                          @RequestParam(defaultValue = "false") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.save(MenuItem.builder().restaurantId(id).name(name)
                .category(category).price(price).isAvailable(isAvailable).build());
        return "redirect:/admin/restaurants/" + id + "/menu";
    }

    @GetMapping("/restaurants/{id}/menu/{itemId}/edit")
    public String editRestaurantMenuItem(@PathVariable Long id, @PathVariable Long itemId,
                                          HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        var restaurant = restaurantService.findById(id).orElse(null);
        if (restaurant == null) return "redirect:/admin/restaurants";
        model.addAttribute("restaurant", restaurant);
        menuItemService.findById(itemId).ifPresent(i -> model.addAttribute("item", i));
        if (model.getAttribute("item") == null) return "redirect:/admin/restaurants/" + id + "/menu";
        model.addAttribute("formAction", "/admin/restaurants/" + id + "/menu/" + itemId + "/update");
        return "admin/restaurant-menu-form";
    }

    @PostMapping("/restaurants/{id}/menu/{itemId}/update")
    public String updateRestaurantMenuItem(@PathVariable Long id, @PathVariable Long itemId,
                                            @RequestParam String name, @RequestParam String category,
                                            @RequestParam BigDecimal price,
                                            @RequestParam(defaultValue = "false") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.findById(itemId).ifPresent(i -> {
            i.setName(name); i.setCategory(category); i.setPrice(price); i.setAvailable(isAvailable);
            menuItemService.save(i);
        });
        return "redirect:/admin/restaurants/" + id + "/menu";
    }

    @PostMapping("/restaurants/{id}/menu/{itemId}/delete")
    public String deleteRestaurantMenuItem(@PathVariable Long id, @PathVariable Long itemId, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.deleteById(itemId);
        return "redirect:/admin/restaurants/" + id + "/menu";
    }

    // === MENU ===
    @GetMapping("/menu")
    public String menu(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("items", menuItemService.findAll());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "admin/menu";
    }

    @GetMapping("/menu/new")
    public String newMenuItem(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("item", new MenuItem());
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("formAction", "/admin/menu/save");
        return "admin/menu-form";
    }

    @PostMapping("/menu/save")
    public String saveMenuItem(@RequestParam Long restaurantId, @RequestParam String name,
                                @RequestParam String category, @RequestParam BigDecimal price,
                                @RequestParam(defaultValue = "false") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.save(MenuItem.builder().restaurantId(restaurantId).name(name)
                .category(category).price(price).isAvailable(isAvailable).build());
        return "redirect:/admin/menu";
    }

    @GetMapping("/menu/{id}/edit")
    public String editMenuItem(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.findById(id).ifPresent(i -> model.addAttribute("item", i));
        model.addAttribute("restaurants", restaurantService.findAll());
        model.addAttribute("formAction", "/admin/menu/" + id + "/update");
        return "admin/menu-form";
    }

    @PostMapping("/menu/{id}/update")
    public String updateMenuItem(@PathVariable Long id, @RequestParam Long restaurantId,
                                  @RequestParam String name, @RequestParam String category,
                                  @RequestParam BigDecimal price,
                                  @RequestParam(defaultValue = "false") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.findById(id).ifPresent(i -> {
            i.setRestaurantId(restaurantId); i.setName(name); i.setCategory(category);
            i.setPrice(price); i.setAvailable(isAvailable);
            menuItemService.save(i);
        });
        return "redirect:/admin/menu";
    }

    @PostMapping("/menu/{id}/delete")
    public String deleteMenuItem(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        menuItemService.deleteById(id);
        return "redirect:/admin/menu";
    }

    // === ORDERS ===
    @GetMapping("/orders")
    public String orders(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        paymentService.syncOrderStatusFromPayments();
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable Long id, @RequestParam String status, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        orderService.updateStatus(id, status);
        return "redirect:/admin/orders";
    }

    // === PAYMENTS ===
    @GetMapping("/payments")
    public String payments(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("payments", paymentService.findAll());
        return "admin/payments";
    }

    @PostMapping("/payments/{id}/status")
    public String updatePaymentStatus(@PathVariable Long id, @RequestParam String status, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        paymentService.updateStatus(id, status);
        return "redirect:/admin/payments";
    }

    @PostMapping("/payments/{id}/approve")
    public String approvePayment(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        paymentService.updateStatus(id, "COMPLETED");
        return "redirect:/admin/payments";
    }
}
