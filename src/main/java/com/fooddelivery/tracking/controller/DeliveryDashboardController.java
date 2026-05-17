package com.fooddelivery.tracking.controller;

import com.fooddelivery.tracking.entity.DeliveryOrder;
import com.fooddelivery.tracking.entity.DeliveryPerson;
import com.fooddelivery.tracking.enums.DeliveryStatus;
import com.fooddelivery.tracking.repository.DeliveryPersonRepository;
import com.fooddelivery.tracking.service.TrackingService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/delivery")
public class DeliveryDashboardController {

    private final TrackingService trackingService;
    private final DeliveryPersonRepository deliveryPersonRepository;

    public DeliveryDashboardController(TrackingService trackingService, DeliveryPersonRepository deliveryPersonRepository) {
        this.trackingService = trackingService;
        this.deliveryPersonRepository = deliveryPersonRepository;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        model.addAttribute("assignedOrders", trackingService.getOrdersByDeliveryPerson(deliveryPerson));
        model.addAttribute("deliveryPerson", deliveryPerson);
        return "delivery/dashboard";
    }

    @PostMapping("/orders/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        if (status == null || status.isBlank()) {
            return "redirect:/delivery/dashboard";
        }

        DeliveryOrder order = trackingService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Delivery person not found"));

        if (order.getDeliveryPerson() == null ||
                !order.getDeliveryPerson().getId().equals(deliveryPerson.getId())) {
            return "redirect:/delivery/dashboard";
        }

        try {
            DeliveryStatus deliveryStatus = DeliveryStatus.valueOf(status.toUpperCase());
            trackingService.updateOrderStatus(id, deliveryStatus);
        } catch (IllegalArgumentException e) {
            return "redirect:/delivery/dashboard";
        }
        return "redirect:/delivery/dashboard";
    }
}
