package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.MenuItemService;
import com.fooddelivery.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/restaurants")
@RequiredArgsConstructor
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;

    @GetMapping("/{id}/menu")
    public String restaurantMenu(@PathVariable Long id, Model model) {
        restaurantService.findById(id).ifPresent(r -> model.addAttribute("restaurant", r));
        model.addAttribute("items", menuItemService.findByRestaurantId(id));
        return "menu";
    }
}
