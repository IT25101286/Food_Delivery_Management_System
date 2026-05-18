package com.fooddelivery.service.interfaces;

import com.fooddelivery.entity.Order;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrderService {
    Order placeOrder(Long userId, String customerName, String deliveryAddress, Long restaurantId, Map<Long, Integer> quantities);
    void updateOrder(Long id, Long userId, String deliveryAddress, Long restaurantId, Map<Long, Integer> quantities);
    void cancelOrder(Long id, Long userId);
    void deleteOrder(Long id, Long userId);
    Map<Long, Integer> getQuantities(Long id);
    Optional<Order> findById(Long id);
    Optional<Order> findByOrderRef(String orderRef);
    List<Order> findByUserId(Long userId);
    List<Order> findAll();
    void updateStatus(Long id, String status);
    Map<String, Long> getStatusCounts();
}
