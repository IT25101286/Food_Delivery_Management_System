package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItem;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.Restaurant;
import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodItemRepository foodItemRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    // ==================== Restaurant List ====================

    @GetMapping("/restaurants")
    public String restaurantList(Model model) {
        model.addAttribute("restaurants",
                restaurantRepository.findByOpenTrue());
        return "order/restaurant-list";
    }

    // ==================== Browse Menu (per restaurant) ====================

    @GetMapping("/menu/{restaurantId}")
    public String browseMenu(
            @PathVariable Long restaurantId,
            @RequestParam(required = false) String category,
            Model model,
            RedirectAttributes redirectAttributes) {

        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElse(null);

        if (restaurant == null) {
            redirectAttributes.addFlashAttribute("error",
                    "Restaurant not found!");
            return "redirect:/order/restaurants";
        }

        List<FoodItem> foodItems;
        if (category != null && !category.isEmpty()) {
            foodItems = foodItemRepository
                    .findByCategoryAndAvailableTrueAndRestaurantId(
                            category, restaurantId);
            model.addAttribute("activeCategory", category);
        } else {
            foodItems = foodItemRepository
                    .findByAvailableTrueAndRestaurantId(restaurantId);
        }

        // Get distinct categories for this restaurant only
        List<String> categories = foodItemRepository
                .findByAvailableTrueAndRestaurantId(restaurantId)
                .stream()
                .map(FoodItem::getCategory)
                .distinct()
                .toList();

        model.addAttribute("restaurant", restaurant);
        model.addAttribute("foodItems", foodItems);
        model.addAttribute("categories", categories);

        return "order/menu";
    }

    // ==================== Cart ====================

    @PostMapping("/cart/{restaurantId}")
    public String addToCart(
            @PathVariable Long restaurantId,
            @RequestParam Map<String, String> params,
            jakarta.servlet.http.HttpSession session,
            RedirectAttributes redirectAttributes) {

        Map<Long, Integer> cart = (Map<Long, Integer>)
                session.getAttribute("cart");
        if (cart == null) {
            cart = new HashMap<>();
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().startsWith("quantity_")) {
                Long foodItemId = Long.parseLong(
                        entry.getKey().replace("quantity_", ""));
                int quantity = Integer.parseInt(entry.getValue());
                if (quantity > 0 && quantity <= 25) {
                    // ✅ if item already in cart, add to existing quantity
                    int existingQuantity = cart.getOrDefault(foodItemId, 0);
                    int newQuantity = existingQuantity + quantity;
                    // cap at 25
                    cart.put(foodItemId, Math.min(newQuantity, 25));
                }
            }
        }

        session.setAttribute("cart", cart);
        redirectAttributes.addFlashAttribute("success",
                "Items added to cart!");
        return "redirect:/order/restaurants";
    }

    @GetMapping("/cart")
    public String viewCart(
            jakarta.servlet.http.HttpSession session,
            Model model) {

        Map<Long, Integer> cart = (Map<Long, Integer>)
                session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            model.addAttribute("empty", true);
            return "order/cart";
        }

        // Group cart items by restaurant
        Map<String, List<Map<String, Object>>> itemsByRestaurant
                = new java.util.LinkedHashMap<>();
        Map<String, java.math.BigDecimal> deliveryFeeByRestaurant
                = new java.util.LinkedHashMap<>();

        for (Map.Entry<Long, Integer> entry : cart.entrySet()) {
            FoodItem food = foodItemRepository
                    .findById(entry.getKey()).orElseThrow();

            String restaurantName = food.getRestaurant() != null
                    ? food.getRestaurant().getName()
                    : "Unknown Restaurant";

            java.math.BigDecimal subtotal = food.getPrice()
                    .multiply(java.math.BigDecimal.valueOf(entry.getValue()));

            Map<String, Object> cartItemMap = new HashMap<>();
            cartItemMap.put("foodItem", food);
            cartItemMap.put("quantity", entry.getValue());
            cartItemMap.put("subtotal", subtotal);

            itemsByRestaurant
                    .computeIfAbsent(restaurantName, k -> new java.util.ArrayList<>())
                    .add(cartItemMap);

            // Add delivery fee per restaurant (only once per restaurant)
            if (!deliveryFeeByRestaurant.containsKey(restaurantName)
                    && food.getRestaurant() != null) {
                deliveryFeeByRestaurant.put(restaurantName,
                        food.getRestaurant().getDeliveryFee());
            }
        }

        java.math.BigDecimal foodTotal = orderService.calculateTotal(cart);
        java.math.BigDecimal deliveryTotal = orderService.calculateDeliveryFee(cart);
        java.math.BigDecimal grandTotal = foodTotal.add(deliveryTotal);

        model.addAttribute("itemsByRestaurant", itemsByRestaurant);
        model.addAttribute("deliveryFeeByRestaurant", deliveryFeeByRestaurant);
        model.addAttribute("foodTotal", foodTotal);
        model.addAttribute("deliveryTotal", deliveryTotal);
        model.addAttribute("grandTotal", grandTotal);
        model.addAttribute("total", grandTotal);

        return "order/cart";
    }

    @PostMapping("/cart/remove/{id}")
    public String removeFromCart(
            @PathVariable Long id,
            jakarta.servlet.http.HttpSession session) {
        Map<Long, Integer> cart = (Map<Long, Integer>)
                session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/order/cart";
    }

    @PostMapping("/cart/clear")
    public String clearCart(jakarta.servlet.http.HttpSession session) {
        session.removeAttribute("cart");
        return "redirect:/order/restaurants";
    }

    // ==================== Checkout ====================

    @PostMapping("/checkout")
    public String checkout(
            jakarta.servlet.http.HttpSession session,
            RedirectAttributes redirectAttributes) {

        Map<Long, Integer> cart = (Map<Long, Integer>)
                session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            redirectAttributes.addFlashAttribute("error",
                    "Your cart is empty!");
            return "redirect:/order/restaurants";
        }

        Order order = orderService.createPendingOrder(cart);
        session.removeAttribute("cart");

        // Skip payment — payment team will wire this in
        orderService.confirmOrder(order.getId());

        return "redirect:/order/success?orderId=" + order.getId();
    }

    // ==================== Order History ====================

    @GetMapping("/history")
    public String orderHistory(Model model) {
        model.addAttribute("orders",
                orderService.getAllOrders());
        return "order/history";
    }

    // ==================== Order Details ====================

    @GetMapping("/details/{id}")
    public String orderDetails(
            @PathVariable Long id,
            Model model,
            RedirectAttributes redirectAttributes) {
        try {
            Order order = orderService.getOrderById(id);
            model.addAttribute("order", order);
            return "order/details";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    "Order not found!");
            return "redirect:/order/history";
        }
    }

    // ==================== Cancel Order ====================

    @PostMapping("/cancel/{id}")
    public String cancelOrder(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {
        try {
            orderService.cancelOrder(id);
            redirectAttributes.addFlashAttribute("success",
                    "Order cancelled successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error",
                    e.getMessage());
        }
        return "redirect:/order/history";
    }

    // ==================== Success Page ====================

    @GetMapping("/success")
    public String orderSuccess(
            @RequestParam Long orderId,
            Model model) {
        Order order = orderService.getOrderById(orderId);
        model.addAttribute("order", order);

        if (order.getTotalAmount().doubleValue() > 2500.0) {
            java.math.BigDecimal discount = order.getTotalAmount()
                    .multiply(java.math.BigDecimal.valueOf(0.10))
                    .setScale(2, java.math.RoundingMode.HALF_UP);
            java.math.BigDecimal finalTotal = order.getTotalAmount()
                    .subtract(discount)
                    .setScale(2, java.math.RoundingMode.HALF_UP)
                    .add(order.getDeliveryFee());
            model.addAttribute("isBulkOrder", true);
            model.addAttribute("discount", discount);
            model.addAttribute("finalTotal", finalTotal);
        }
        return "order/success";
    }

    // ==================== Coming Soon ====================

    @GetMapping("/coming-soon")
    public String comingSoon() {
        return "order/coming-soon";
    }

    // ==================== Home Redirect ====================

    @GetMapping("/")
    public String home() {
        return "redirect:/order/restaurants";
    }
}