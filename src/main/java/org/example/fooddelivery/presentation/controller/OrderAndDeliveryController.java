package org.example.fooddelivery.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.presentation.service.DeliveryService;
import org.example.fooddelivery.presentation.service.MenuItemService;
import org.example.fooddelivery.presentation.service.OrderService;
import org.example.fooddelivery.presentation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class OrderAndDeliveryController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final MenuItemService menuItemService;
    private final UserService userService;

    @PostMapping("/order")
    public String showOrderForm(
            @RequestParam List<Long> selectedItemsIds,
            @RequestParam List<Integer> quantities,
            Model model
    ) {
        List<MenuItem> selectedMenuItems = new ArrayList<>();
        for (int i = 0; i < selectedItemsIds.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                selectedMenuItems.add(menuItemService.getMenuItemById(selectedItemsIds.get(i)));
            }
        }
        BigDecimal totalPrice = selectedMenuItems.stream()
                                                 .map(MenuItem::getPrice)
                                                 .reduce(BigDecimal.ZERO, BigDecimal::add);
        model.addAttribute("selectedMenuItems", selectedMenuItems);
        model.addAttribute("totalPrice", totalPrice);

        Order order = Order.builder()
                           .status(OrderStatus.NEW)
                           .itemList(selectedMenuItems)
                           .totalPrice(totalPrice)
                           .orderDate(LocalDateTime.now())
                           .build();

        Delivery delivery = Delivery.builder()
                                    .order(order)
                                    .build();
        User user = new User();

        model.addAttribute("order", order);
        model.addAttribute("delivery", delivery);
        model.addAttribute("user", user);

        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit(
            @ModelAttribute Order order,
            @ModelAttribute Delivery delivery,
            @ModelAttribute User user
    ) {
        order.setUser(user);
        orderService.createOrder(order);

        orderService.createOrder(delivery.getOrder());
        deliveryService.createDelivery(delivery);
        userService.createUser(user);

        return "redirect:/menu";
    }
}
