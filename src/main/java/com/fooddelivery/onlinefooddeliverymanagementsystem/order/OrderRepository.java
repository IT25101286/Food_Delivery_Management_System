package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Get orders by status
    List<Order> findByStatus(OrderStatus status);

    // Get all orders sorted by latest first
    List<Order> findAllByOrderByCreatedAtDesc();
}