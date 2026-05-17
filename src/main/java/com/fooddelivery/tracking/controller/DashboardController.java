package com.fooddelivery.tracking.controller;

import com.fooddelivery.tracking.entity.DeliveryPerson;
import com.fooddelivery.tracking.enums.DeliveryStatus;
import com.fooddelivery.tracking.service.DeliveryService;
import com.fooddelivery.tracking.service.TrackingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class DashboardController {

    private final TrackingService trackingService;
    private final DeliveryService deliveryService;

    public DashboardController(TrackingService trackingService, DeliveryService deliveryService) {
        this.trackingService = trackingService;
        this.deliveryService = deliveryService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        long totalToday = trackingService.countTodayByStatus(DeliveryStatus.PENDING) +
                trackingService.countTodayByStatus(DeliveryStatus.ASSIGNED) +
                trackingService.countTodayByStatus(DeliveryStatus.PICKED_UP) +
                trackingService.countTodayByStatus(DeliveryStatus.ON_THE_WAY) +
                trackingService.countTodayByStatus(DeliveryStatus.DELIVERED);

        model.addAttribute("totalDeliveriesToday", totalToday);
        model.addAttribute("pendingCount", trackingService.countTodayByStatus(DeliveryStatus.PENDING));
        model.addAttribute("onTheWayCount", trackingService.countTodayByStatus(DeliveryStatus.ON_THE_WAY));
        model.addAttribute("deliveredCount", trackingService.countTodayByStatus(DeliveryStatus.DELIVERED));
        model.addAttribute("recentOrders", trackingService.getAllOrders().stream().limit(10).toList());

        return "admin/dashboard";
    }
}
