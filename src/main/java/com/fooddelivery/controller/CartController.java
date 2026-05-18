package com.fooddelivery.controller;

import com.fooddelivery.cart.CartItem;
import com.fooddelivery.service.interfaces.MenuItemService;
import com.fooddelivery.service.interfaces.OrderService;
import com.fooddelivery.service.interfaces.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class CartController {
    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    @GetMapping("/cart")
    public String cartPage(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        List<CartItem> cart = getCart(session);
        BigDecimal total = cart.stream().map(CartItem::getSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        // Group items by restaurantName for display
        Map<String, List<CartItem>> grouped = new LinkedHashMap<>();
        for (CartItem ci : cart) {
            grouped.computeIfAbsent(ci.getRestaurantName(), k -> new ArrayList<>()).add(ci);
        }

        model.addAttribute("cart", cart);
        model.addAttribute("groupedCart", grouped);
        model.addAttribute("cartTotal", total);
        return "cart";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long itemId,
                            @RequestParam(defaultValue = "1") int quantity,
                            @RequestParam Long restaurantId,
                            HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        var item = menuItemService.findById(itemId).orElse(null);
        var restaurant = restaurantService.findById(restaurantId).orElse(null);
        if (item == null || restaurant == null) return "redirect:/restaurants/" + restaurantId + "/menu";

        List<CartItem> cart = getCart(session);
        int qty = Math.max(1, Math.min(20, quantity));

        var existing = cart.stream().filter(ci -> ci.getItemId().equals(itemId)).findFirst();
        if (existing.isPresent()) {
            existing.get().setQuantity(Math.min(20, existing.get().getQuantity() + qty));
        } else {
            cart.add(new CartItem(restaurantId, restaurant.getName(), itemId, item.getName(), item.getPrice(), qty));
        }

        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.stream().mapToInt(CartItem::getQuantity).sum());
        return "redirect:/restaurants/" + restaurantId + "/menu";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long itemId,
                             @RequestParam int quantity,
                             HttpSession session) {
        List<CartItem> cart = getCart(session);
        if (quantity <= 0) {
            cart.removeIf(ci -> ci.getItemId().equals(itemId));
        } else {
            cart.stream().filter(ci -> ci.getItemId().equals(itemId)).findFirst()
                    .ifPresent(ci -> ci.setQuantity(Math.min(20, quantity)));
        }
        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", cart.stream().mapToInt(CartItem::getQuantity).sum());
        return "redirect:/cart";
    }

    @PostMapping("/cart/place")
    public String placeFromCart(@RequestParam String deliveryAddress, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        List<CartItem> cart = getCart(session);
        if (cart.isEmpty()) return "redirect:/cart";

        Long uid = (Long) session.getAttribute("userId");
        String name = (String) session.getAttribute("userName");

        // Group by restaurantId and create one order per restaurant
        Map<Long, List<CartItem>> byRestaurant = new LinkedHashMap<>();
        for (CartItem ci : cart) {
            byRestaurant.computeIfAbsent(ci.getRestaurantId(), k -> new ArrayList<>()).add(ci);
        }
        for (Map.Entry<Long, List<CartItem>> entry : byRestaurant.entrySet()) {
            Map<Long, Integer> quantities = entry.getValue().stream()
                    .collect(Collectors.toMap(CartItem::getItemId, CartItem::getQuantity));
            orderService.placeOrder(uid, name, deliveryAddress, entry.getKey(), quantities);
        }

        cart.clear();
        session.setAttribute("cart", cart);
        session.setAttribute("cartCount", 0);
        return "redirect:/order/my-orders";
    }

    @SuppressWarnings("unchecked")
    private List<CartItem> getCart(HttpSession session) {
        var cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
