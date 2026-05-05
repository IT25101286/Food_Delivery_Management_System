package com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // Get all available food items (for customer ordering)
    List<FoodItem> findByAvailableTrue();

    // Get food items by category
    List<FoodItem> findByCategory(String category);

    // Search food items by name
    List<FoodItem> findByNameContainingIgnoreCase(String name);

    // Get available items by category
    List<FoodItem> findByCategoryAndAvailableTrue(String category);
}