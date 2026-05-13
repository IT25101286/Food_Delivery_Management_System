package com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // Get all open restaurants for customer view
    List<Restaurant> findByOpenTrue();
}