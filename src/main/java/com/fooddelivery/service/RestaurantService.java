package com.fooddelivery.service;

import com.fooddelivery.entity.Restaurant;
import com.fooddelivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public List<Restaurant> getOpenRestaurants() {
        return restaurantRepository.findByStatus("OPEN");
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Long id, Restaurant restaurantDetails) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant != null) {
            restaurant.setName(restaurantDetails.getName());
            restaurant.setAddress(restaurantDetails.getAddress());
            restaurant.setPhone(restaurantDetails.getPhone());
            restaurant.setCuisineType(restaurantDetails.getCuisineType());
            restaurant.setStatus(restaurantDetails.getStatus());
            restaurant.setOpeningTime(restaurantDetails.getOpeningTime());
            restaurant.setClosingTime(restaurantDetails.getClosingTime());
            restaurant.setOwnerName(restaurantDetails.getOwnerName());
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    public void deleteRestaurant(Long id) {
        restaurantRepository.deleteById(id);
    }

    public Restaurant toggleRestaurantStatus(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id).orElse(null);
        if (restaurant != null) {
            if ("OPEN".equals(restaurant.getStatus())) {
                restaurant.setStatus("CLOSED");
            } else if ("CLOSED".equals(restaurant.getStatus())) {
                restaurant.setStatus("OPEN");
            }
            return restaurantRepository.save(restaurant);
        }
        return null;
    }

    public long getTotalRestaurantCount() {
        return restaurantRepository.count();
    }

    public long getOpenRestaurantCount() {
        return restaurantRepository.countByStatus("OPEN");
    }

    public long getSuspendedRestaurantCount() {
        return restaurantRepository.countByStatus("SUSPENDED");
    }
}
