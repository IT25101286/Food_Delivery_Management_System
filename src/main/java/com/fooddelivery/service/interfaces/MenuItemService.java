package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.MenuItem;
import java.util.List;
import java.util.Optional;

public interface MenuItemService {
    MenuItem save(MenuItem item);
    Optional<MenuItem> findById(Long id);
    List<MenuItem> findAll();
    List<MenuItem> findAvailable();
    List<MenuItem> findByRestaurantId(Long restaurantId);
    void deleteById(Long id);
}
