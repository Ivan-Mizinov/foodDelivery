package org.example.fooddelivery.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.presentation.service.*;
import org.example.fooddelivery.presentation.service.dto.OrderDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OrderAndDeliveryController {
    private final OrderService orderService;
    private final DeliveryService deliveryService;
    private final UserService userService;
    private final SessionInfoService sessionInfoService;

    @GetMapping("/order")
    public String showOrder(Model model) {
        if (sessionInfoService.getCart() == null ||
                sessionInfoService.getCart().isEmpty()) {
            return "redirect:/menu";
        }
        model.addAttribute("orderDto", new OrderDto(
                sessionInfoService.getUsername(),
                sessionInfoService.getAddress(),
                sessionInfoService.getPhone()));
        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit(
            @Valid @ModelAttribute("orderDto") OrderDto orderDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("sessionInfoService", sessionInfoService);
            model.addAttribute("orderDto", orderDto);
            return "order";
        }
        sessionInfoService.setInfoFromOrderDto(orderDto);

        IUser user = userService.getUserByEmail(sessionInfoService.getEmail());
        user.setAddress(sessionInfoService.getAddress());
        user.setPhone(sessionInfoService.getPhone());
        user.setName(sessionInfoService.getUsername());

        IOrder order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .itemList(sessionInfoService.getCart())
                .totalPrice(sessionInfoService.getTotalPrice())
                .orderDate(LocalDateTime.now())
                .build();

        IDelivery delivery = Delivery.builder()
                .order(order)
                .deliveryTime(LocalDateTime.now())
                .phone(sessionInfoService.getPhone())
                .address(sessionInfoService.getAddress())
                .build();

        orderService.createOrder(delivery.getOrder());
        deliveryService.createDelivery(delivery);

        return "redirect:/menu";
    }
}
