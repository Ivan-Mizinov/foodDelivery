package org.example.fooddelivery.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.presentation.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@SessionAttributes("filteredOrders")
public class AdminController {

    private final OrderService orderService;

    @GetMapping
    public String showAdminPanel(Model model) {
        model.addAttribute("newOrders", orderService.getOrdersByStatus(OrderStatus.NEW));
        if (!model.containsAttribute("filteredOrders")) {
            model.addAttribute("filteredOrders", Collections.emptyList());
        }
        return "admin";
    }

    @PostMapping("orders/update/{id}")
    public String updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status
    ) {
        orderService.updateOrderStatus(id, status);
        log.info("Order status updated to {}", status);
        return "redirect:/admin";
    }

    @GetMapping("orders/filter")
    public String getOrdersFilteredByStatus(
            @RequestParam OrderStatus status,
            Model model
    ) {
        model.addAttribute("newOrders", orderService.getOrdersByStatus(OrderStatus.NEW));
        model.addAttribute("filteredOrders", orderService.getOrdersByStatus(status));
        return "admin";
    }
}
