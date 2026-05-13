package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItem;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.OrderStatus;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.Restaurant;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private OrderSummaryService orderSummaryService;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // ==================== Place Order ====================

    public Order createPendingOrder(Map<Long, Integer> cartItems) {

        List<Order.OrderItem> orderItems = new ArrayList<>();
        BigDecimal foodTotal = BigDecimal.ZERO;

        // Track unique restaurants in this order for delivery fee
        Set<Long> restaurantIds = new HashSet<>();

        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            FoodItem foodItem = foodItemRepository.findById(entry.getKey())
                    .orElseThrow(() -> new RuntimeException("Food item not found!"));

            // Get restaurant info for this item
            Long restaurantId = null;
            String restaurantName = "Unknown";
            if (foodItem.getRestaurant() != null) {
                restaurantId = foodItem.getRestaurant().getId();
                restaurantName = foodItem.getRestaurant().getName();
                restaurantIds.add(restaurantId);
            }

            Order.OrderItem orderItem = new Order.OrderItem(
                    foodItem.getId(),
                    foodItem.getName(),
                    entry.getValue(),
                    foodItem.getPrice(),
                    restaurantId,
                    restaurantName
            );
            orderItems.add(orderItem);
            foodTotal = foodTotal.add(foodItem.getPrice()
                    .multiply(BigDecimal.valueOf(entry.getValue())));
        }

        // Calculate combined delivery fee from all restaurants
        BigDecimal totalDeliveryFee = BigDecimal.ZERO;
        for (Long restaurantId : restaurantIds) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElse(null);
            if (restaurant != null) {
                totalDeliveryFee = totalDeliveryFee.add(restaurant.getDeliveryFee());
            }
        }

        Order order = new Order();
        order.setCustomerName("Guest Customer");
        order.setCustomerAddress("Default Address");
        order.setCustomerPhone("0000000000");
        order.setItems(orderItems);
        order.setTotalAmount(foodTotal);
        order.setDeliveryFee(totalDeliveryFee);
        order.setStatus(OrderStatus.PENDING);

        // Demonstrate polymorphism
        String orderType = orderSummaryService.getOrderType(
                orderSummaryService.createOrderByAmount(foodTotal));
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
            throw new RuntimeException("Order cannot be cancelled!");
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

    // ==================== Calculate Delivery Fee ====================

    public BigDecimal calculateDeliveryFee(Map<Long, Integer> cartItems) {
        Set<Long> restaurantIds = new HashSet<>();
        for (Long foodItemId : cartItems.keySet()) {
            FoodItem foodItem = foodItemRepository.findById(foodItemId)
                    .orElse(null);
            if (foodItem != null && foodItem.getRestaurant() != null) {
                restaurantIds.add(foodItem.getRestaurant().getId());
            }
        }

        BigDecimal totalDeliveryFee = BigDecimal.ZERO;
        for (Long restaurantId : restaurantIds) {
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElse(null);
            if (restaurant != null) {
                totalDeliveryFee = totalDeliveryFee.add(restaurant.getDeliveryFee());
            }
        }
        return totalDeliveryFee;
    }
}