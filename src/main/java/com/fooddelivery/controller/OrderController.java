package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.MenuItemService;
import com.fooddelivery.service.interfaces.OrderService;
import com.fooddelivery.service.interfaces.PaymentService;
import com.fooddelivery.service.interfaces.RestaurantService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;
    private final PaymentService paymentService;

    @GetMapping("/place")
    public String placePage(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        model.addAttribute("items", menuItemService.findAvailable());
        model.addAttribute("restaurants", restaurantService.findOpen());
        return "order/place";
    }

    @PostMapping("/place")
    public String placeOrder(@RequestParam Map<String, String> allParams,
                             @RequestParam String deliveryAddress,
                             @RequestParam Long restaurantId,
                             HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        Map<Long, Integer> quantities = new HashMap<>();
        for (Map.Entry<String, String> e : allParams.entrySet()) {
            if (e.getKey().startsWith("qty_")) {
                try {
                    Long itemId = Long.parseLong(e.getKey().substring(4));
                    int qty = Integer.parseInt(e.getValue());
                    if (qty > 0) quantities.put(itemId, qty);
                } catch (NumberFormatException ignored) {}
            }
        }
        if (quantities.isEmpty()) return "redirect:/order/place";
        Long uid = (Long) session.getAttribute("userId");
        String name = (String) session.getAttribute("userName");
        orderService.placeOrder(uid, name, deliveryAddress, restaurantId, quantities);
        return "redirect:/order/my-orders";
    }

    @GetMapping("/my-orders")
    public String myOrders(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        Long uid = (Long) session.getAttribute("userId");
        paymentService.syncOrderStatusFromPayments();
        model.addAttribute("orders", orderService.findByUserId(uid));
        return "order/my-orders";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        Long uid = (Long) session.getAttribute("userId");
        var order = orderService.findById(id)
                .filter(o -> o.getUserId().equals(uid) && "PENDING".equals(o.getStatus()))
                .orElse(null);
        if (order == null) return "redirect:/order/my-orders";
        model.addAttribute("order", order);
        model.addAttribute("restaurants", restaurantService.findOpen());
        model.addAttribute("items", menuItemService.findAvailable());
        model.addAttribute("currentQuantities", orderService.getQuantities(id));
        return "order/edit";
    }

    @PostMapping("/{id}/edit")
    public String editOrder(@PathVariable Long id,
                            @RequestParam Map<String, String> allParams,
                            @RequestParam String deliveryAddress,
                            @RequestParam Long restaurantId,
                            HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        Long uid = (Long) session.getAttribute("userId");
        Map<Long, Integer> quantities = new HashMap<>();
        for (Map.Entry<String, String> e : allParams.entrySet()) {
            if (e.getKey().startsWith("qty_")) {
                try {
                    Long itemId = Long.parseLong(e.getKey().substring(4));
                    int qty = Integer.parseInt(e.getValue());
                    if (qty > 0) quantities.put(itemId, qty);
                } catch (NumberFormatException ignored) {}
            }
        }
        if (quantities.isEmpty()) return "redirect:/order/" + id + "/edit";
        orderService.updateOrder(id, uid, deliveryAddress, restaurantId, quantities);
        return "redirect:/order/my-orders";
    }

    @PostMapping("/{id}/cancel")
    public String cancel(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        orderService.cancelOrder(id, (Long) session.getAttribute("userId"));
        return "redirect:/order/my-orders";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        orderService.deleteOrder(id, (Long) session.getAttribute("userId"));
        return "redirect:/order/my-orders";
    }
}
