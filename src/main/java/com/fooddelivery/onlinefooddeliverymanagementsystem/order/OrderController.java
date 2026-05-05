package com.fooddelivery.onlinefooddeliverymanagementsystem.order;

import com.fooddelivery.onlinefooddeliverymanagementsystem.restaurant.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private FoodItemRepository foodItemRepository;

    // ==================== Browse Menu ====================

    @GetMapping("/menu")
    public String browseMenu(Model model) {
        model.addAttribute("foodItems",
                foodItemRepository.findByAvailableTrue());
        model.addAttribute("categories",
                foodItemRepository.findAll()
                        .stream()
                        .map(item -> item.getCategory())
                        .distinct()
                        .toList());
        return "order/menu";
    }

    // ==================== Cart ====================

    @PostMapping("/cart")
    public String addToCart(
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
                    cart.put(foodItemId, quantity);
                }
            }
        }

        session.setAttribute("cart", cart);
        return "redirect:/order/cart";
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

        model.addAttribute("cartItems",
                cart.entrySet().stream().map(entry -> {
                    var food = foodItemRepository
                            .findById(entry.getKey()).orElseThrow();
                    return Map.of(
                            "foodItem", food,
                            "quantity", entry.getValue(),
                            "subtotal", food.getPrice()
                                    .multiply(java.math.BigDecimal
                                            .valueOf(entry.getValue()))
                    );
                }).toList());

        model.addAttribute("total",
                orderService.calculateTotal(cart));
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
        return "redirect:/order/menu";
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
            return "redirect:/order/menu";
        }

        Order order = orderService.createPendingOrder(cart);
        session.removeAttribute("cart");

        // Skip payment — go directly to success
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

        // Calculate discount if bulk order
        if (order.getTotalAmount().doubleValue() > 2500.0) {
            java.math.BigDecimal discount = order.getTotalAmount()
                    .multiply(java.math.BigDecimal.valueOf(0.10))
                    .setScale(2, java.math.RoundingMode.HALF_UP);
            java.math.BigDecimal finalTotal = order.getTotalAmount()
                    .subtract(discount)
                    .setScale(2, java.math.RoundingMode.HALF_UP);
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
        return "redirect:/order/menu";
    }
}