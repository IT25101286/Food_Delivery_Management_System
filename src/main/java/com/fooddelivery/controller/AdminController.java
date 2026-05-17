package com.fooddelivery.controller;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.service.RestaurantService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestaurantService restaurantService;

    private boolean isAdminLoggedIn(HttpSession session) {
        return session.getAttribute("adminId") != null;
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpSession session, Model model) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        long total = restaurantService.getTotalRestaurantCount();
        long open = restaurantService.getOpenRestaurantCount();
        long suspended = restaurantService.getSuspendedRestaurantCount();

        model.addAttribute("totalRestaurants", total);
        model.addAttribute("openRestaurants", open);
        model.addAttribute("suspendedRestaurants", suspended);
        model.addAttribute("adminEmail", session.getAttribute("adminEmail"));

        return "admin/dashboard";
    }

    @GetMapping("/restaurants")
    public String listAllRestaurants(HttpSession session, Model model) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "admin/restaurants";
    }

    @GetMapping("/restaurants/new")
    public String createRestaurantForm(HttpSession session, Model model) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        model.addAttribute("restaurant", new Restaurant());
        return "admin/restaurant-form";
    }

    @PostMapping("/restaurants")
    public String saveRestaurant(@ModelAttribute Restaurant restaurant, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        restaurantService.createRestaurant(restaurant);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/restaurants/{id}/edit")
    public String editRestaurantForm(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            model.addAttribute("restaurant", restaurant);
            return "admin/restaurant-form";
        }
        return "redirect:/admin/restaurants";
    }

    @PostMapping("/restaurants/{id}")
    public String updateRestaurant(@PathVariable Long id,
                                   @ModelAttribute Restaurant restaurant,
                                   HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        restaurantService.updateRestaurant(id, restaurant);
        return "redirect:/admin/restaurants";
    }

    @GetMapping("/restaurants/{id}/delete")
    public String deleteRestaurant(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        restaurantService.deleteRestaurant(id);
        return "redirect:/admin/restaurants";
    }

    @PostMapping("/restaurants/{id}/toggle")
    public String toggleRestaurantStatus(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggedIn(session)) {
            return "redirect:/login";
        }

        restaurantService.toggleRestaurantStatus(id);
        return "redirect:/admin/restaurants";
    }
}
