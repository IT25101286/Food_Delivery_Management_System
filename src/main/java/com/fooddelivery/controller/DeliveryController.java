package com.fooddelivery.controller;

import com.fooddelivery.entity.DeliveryOrder;
import com.fooddelivery.entity.DeliveryPerson;
import com.fooddelivery.service.interfaces.DeliveryOrderService;
import com.fooddelivery.service.interfaces.DeliveryPersonService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class DeliveryController {
    private final DeliveryPersonService deliveryPersonService;
    private final DeliveryOrderService deliveryOrderService;

    private boolean notAdmin(HttpSession s) {
        return s.getAttribute("userId") == null || !"ADMIN".equals(s.getAttribute("userRole"));
    }

    // === DELIVERY PERSONS ===
    @GetMapping("/delivery-persons")
    public String deliveryPersons(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("persons", deliveryPersonService.findAll());
        return "admin/delivery-persons";
    }

    @GetMapping("/delivery-persons/new")
    public String newPerson(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("person", new DeliveryPerson());
        model.addAttribute("formAction", "/admin/delivery-persons/save");
        return "admin/delivery-person-form";
    }

    @PostMapping("/delivery-persons/save")
    public String savePerson(@RequestParam String name, @RequestParam String phone,
                              @RequestParam(defaultValue = "true") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryPersonService.save(DeliveryPerson.builder().name(name).phone(phone).isAvailable(isAvailable).build());
        return "redirect:/admin/delivery-persons";
    }

    @GetMapping("/delivery-persons/{id}/edit")
    public String editPerson(@PathVariable Long id, HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryPersonService.findById(id).ifPresent(p -> model.addAttribute("person", p));
        model.addAttribute("formAction", "/admin/delivery-persons/" + id + "/update");
        return "admin/delivery-person-form";
    }

    @PostMapping("/delivery-persons/{id}/update")
    public String updatePerson(@PathVariable Long id, @RequestParam String name, @RequestParam String phone,
                                @RequestParam(defaultValue = "false") boolean isAvailable, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryPersonService.findById(id).ifPresent(p -> {
            p.setName(name); p.setPhone(phone); p.setAvailable(isAvailable);
            deliveryPersonService.save(p);
        });
        return "redirect:/admin/delivery-persons";
    }

    @PostMapping("/delivery-persons/{id}/delete")
    public String deletePerson(@PathVariable Long id, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryPersonService.deleteById(id);
        return "redirect:/admin/delivery-persons";
    }

    // === DELIVERY ORDERS ===
    @GetMapping("/delivery-orders")
    public String deliveryOrders(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("deliveryOrders", deliveryOrderService.findAll());
        model.addAttribute("persons", deliveryPersonService.findAll());
        return "admin/delivery-orders";
    }

    @GetMapping("/delivery-orders/new")
    public String newDeliveryOrder(HttpSession session, Model model) {
        if (notAdmin(session)) return "redirect:/login";
        model.addAttribute("order", new DeliveryOrder());
        model.addAttribute("formAction", "/admin/delivery-orders/save");
        return "admin/delivery-orders";
    }

    @PostMapping("/delivery-orders/save")
    public String saveDeliveryOrder(@RequestParam String orderRef, @RequestParam String customerName,
                                     @RequestParam String deliveryAddress, @RequestParam String restaurantName,
                                     HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryOrderService.save(DeliveryOrder.builder().orderRef(orderRef).customerName(customerName)
                .deliveryAddress(deliveryAddress).restaurantName(restaurantName).status("PENDING").build());
        return "redirect:/admin/delivery-orders";
    }

    @PostMapping("/delivery-orders/{id}/assign")
    public String assign(@PathVariable Long id, @RequestParam Long deliveryPersonId,
                          @RequestParam Integer estimatedMinutes, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryOrderService.assign(id, deliveryPersonId, estimatedMinutes);
        return "redirect:/admin/delivery-orders";
    }

    @PostMapping("/delivery-orders/{id}/status")
    public String updateDeliveryStatus(@PathVariable Long id, @RequestParam String status, HttpSession session) {
        if (notAdmin(session)) return "redirect:/login";
        deliveryOrderService.updateStatus(id, status);
        return "redirect:/admin/delivery-orders";
    }
}
