package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.DeliveryOrderService;
import com.fooddelivery.service.interfaces.OrderService;
import com.fooddelivery.service.interfaces.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final RestaurantService restaurantService;
    private final DeliveryOrderService deliveryOrderService;
    private final OrderService orderService;

    @GetMapping("/restaurants")
    public String restaurants(Model model) {
        model.addAttribute("restaurants", restaurantService.findOpen());
        return "restaurants";
    }

    @GetMapping("/track/{orderRef}")
    public String track(@PathVariable String orderRef, Model model) {
        var deliveryOpt = deliveryOrderService.findByOrderRef(orderRef);
        deliveryOpt.ifPresent(d -> model.addAttribute("delivery", d));
        model.addAttribute("orderRef", orderRef);

        orderService.findByOrderRef(orderRef).ifPresent(o -> {
            model.addAttribute("order", o);
            String restaurantName = restaurantService.findById(o.getRestaurantId())
                    .map(r -> r.getName()).orElse("Restaurant");
            model.addAttribute("restaurantName", restaurantName);

            String deliveryStatus = deliveryOpt.map(d -> d.getStatus()).orElse("");
            int step;
            String message;

            if ("CANCELLED".equals(o.getStatus())) {
                step = 0;
                message = "This order has been cancelled.";
            } else if ("DELIVERED".equals(o.getStatus()) || "DELIVERED".equals(deliveryStatus)) {
                step = 5;
                message = "Your order has been delivered!";
            } else if ("IN_TRANSIT".equals(deliveryStatus) || "ASSIGNED".equals(deliveryStatus)) {
                step = 4;
                message = "On the way to you at " + o.getDeliveryAddress();
            } else if ("PREPARING".equals(o.getStatus())) {
                step = 3;
                message = restaurantName + " is preparing your order";
            } else if ("CONFIRMED".equals(o.getStatus())) {
                step = 2;
                message = "Order confirmed — " + restaurantName + " will start preparing soon";
            } else {
                step = 1;
                message = "Waiting for " + restaurantName + " to confirm your order";
            }

            model.addAttribute("trackStep", step);
            model.addAttribute("trackMessage", message);
        });

        return "track";
    }
}
