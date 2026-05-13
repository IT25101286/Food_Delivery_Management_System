package com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {

    // Existing methods — don't remove these
    List<FoodItem> findByAvailableTrue();
    List<FoodItem> findByCategory(String category);
    List<FoodItem> findByNameContainingIgnoreCase(String name);
    List<FoodItem> findByCategoryAndAvailableTrue(String category);

    // New methods for multi-restaurant support
    List<FoodItem> findByAvailableTrueAndRestaurantId(Long restaurantId);
    List<FoodItem> findByCategoryAndAvailableTrueAndRestaurantId(
            String category, Long restaurantId);
}