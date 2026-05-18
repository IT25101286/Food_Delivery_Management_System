package com.fooddelivery.controller;

import com.fooddelivery.service.interfaces.OrderService;
import com.fooddelivery.service.interfaces.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final OrderService orderService;

    @GetMapping("/pay")
    public String payPage(@RequestParam(required = false) Long orderId, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        if (orderId != null) orderService.findById(orderId).ifPresent(o -> model.addAttribute("order", o));
        else model.addAttribute("orders", orderService.findByUserId((Long) session.getAttribute("userId")));
        return "payment/pay";
    }

    @PostMapping("/pay")
    public String pay(@RequestParam Long orderId, @RequestParam String paymentMethod,
                      HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        var order = orderService.findById(orderId).orElseThrow();
        var payment = paymentService.pay(orderId, order.getOrderRef(),
                (String) session.getAttribute("userName"), paymentMethod);
        model.addAttribute("payment", payment);
        model.addAttribute("order", order);
        return "redirect:/payment/success?paymentId=" + payment.getId();
    }

    @GetMapping("/success")
    public String success(@RequestParam Long paymentId, HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        paymentService.findById(paymentId).ifPresent(p -> {
            model.addAttribute("payment", p);
            orderService.findByOrderRef(p.getOrderRef()).ifPresent(o -> model.addAttribute("order", o));
        });
        return "payment/success";
    }

    @GetMapping("/my-payments")
    public String myPayments(HttpSession session, Model model) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        paymentService.syncOrderStatusFromPayments();
        var orders = orderService.findByUserId((Long) session.getAttribute("userId"));
        Map<Long, java.util.List<com.fooddelivery.entity.Payment>> paymentsMap = new LinkedHashMap<>();
        for (var o : orders) paymentsMap.put(o.getId(), paymentService.findByOrderId(o.getId()));
        model.addAttribute("orders", orders);
        model.addAttribute("paymentsMap", paymentsMap);
        return "payment/my-payments";
    }

    @PostMapping("/{id}/delete")
    public String deletePayment(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("userId") == null) return "redirect:/login";
        paymentService.deleteById(id);
        return "redirect:/payment/my-payments";
    }
}
