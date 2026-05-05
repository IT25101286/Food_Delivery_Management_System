package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.OrderStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

// Demonstrates Polymorphism — same method behaves differently
// based on order type
@Service
public class OrderSummaryService {

    // Polymorphism in action — accepts any Order type
    public String generateSummary(Order order) {
        if (order instanceof BulkOrder bulkOrder) {
            return bulkOrder.getOrderSummary();
        } else if (order instanceof RegularOrder regularOrder) {
            return regularOrder.getOrderSummary();
        }
        return "Order #" + order.getId() + " | Rs." + order.getTotalAmount();
    }

    // Factory method — creates correct order type based on amount
    public Order createOrderByAmount(BigDecimal amount) {
        if (amount != null && amount.doubleValue() > 2500.0) {
            return new BulkOrder();
        }
        return new RegularOrder();
    }

    // Demonstrates polymorphism — works with any Order subtype
    public String getOrderType(Order order) {
        if (order instanceof BulkOrder) {
            return "BULK";
        } else if (order instanceof RegularOrder) {
            return "REGULAR";
        }
        return "STANDARD";
    }

    // Check if order qualifies for cash payment
    public boolean isCashEligible(Order order) {
        if (order instanceof RegularOrder regularOrder) {
            return regularOrder.isCashEligible();
        }
        return false;
    }
}