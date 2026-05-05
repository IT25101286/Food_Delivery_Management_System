package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItem;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private OrderSummaryService orderSummaryService;

    // ==================== Place Order ====================

    public Order createPendingOrder(Map<Long, Integer> cartItems) {

        List<Order.OrderItem> orderItems = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            FoodItem foodItem = foodItemRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Food item not found!"));

            Order.OrderItem orderItem = new Order.OrderItem(
                    foodItem.getId(),
                    foodItem.getName(),
                    entry.getValue(),
                    foodItem.getPrice()
            );
            orderItems.add(orderItem);
            total = total.add(foodItem.getPrice()
                    .multiply(BigDecimal.valueOf(entry.getValue())));
        }

        Order order = new Order();
        order.setCustomerName("Guest Customer");
        order.setCustomerAddress("Default Address");
        order.setCustomerPhone("0000000000");
        order.setItems(orderItems);
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);

        // Demonstrate polymorphism
        String orderType = orderSummaryService.getOrderType(
                orderSummaryService.createOrderByAmount(total));
        System.out.println("Order type: " + orderType);

        return orderRepository.save(order);
    }

    // ==================== Confirm Order ====================

    public Order confirmOrder(Long orderId) {
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.PAYMENT_SUCCESS);
        return orderRepository.save(order);
    }

    // ==================== Get Orders ====================

    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAllByOrderByCreatedAtDesc();
    }

    // ==================== Cancel Order ====================

    public Order cancelOrder(Long orderId) {
        Order order = getOrderById(orderId);

        if (order.getStatus() != OrderStatus.PENDING &&
                order.getStatus() != OrderStatus.PAYMENT_SUCCESS) {
            throw new RuntimeException(
                    "Order cannot be cancelled!");
        }

        order.setStatus(OrderStatus.DECLINED);
        return orderRepository.save(order);
    }

    // ==================== Calculate Total ====================

    public BigDecimal calculateTotal(Map<Long, Integer> cartItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            FoodItem foodItem = foodItemRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Food item not found!"));
            total = total.add(foodItem.getPrice()
                    .multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return total;
    }
}