package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.MenuItemService;
import com.fooddelivery.service.interfaces.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;

    @GetMapping
    public String menu(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("items", menuItemService.findAvailable());
        model.addAttribute("restaurants", restaurantService.findAll());
        return "menu";
    }
}
