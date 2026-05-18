package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.Restaurant;
import java.util.List;
import java.util.Optional;

public interface RestaurantService {
    Restaurant save(Restaurant restaurant);
    Optional<Restaurant> findById(Long id);
    List<Restaurant> findAll();
    List<Restaurant> findOpen();
    void deleteById(Long id);
}
