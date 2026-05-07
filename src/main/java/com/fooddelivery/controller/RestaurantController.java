package com.fooddelivery.controller;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @GetMapping
    public String listOpenRestaurants(Model model) {
        List<Restaurant> restaurants = restaurantService.getOpenRestaurants();
        model.addAttribute("restaurants", restaurants);
        return "restaurants";
    }
}
