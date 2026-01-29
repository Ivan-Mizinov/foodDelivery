package org.example.fooddelivery.presentation.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.presentation.service.*;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    private final SessionInfoService sessionInfoService;
    private final MessageSource messageSource;

    @GetMapping("/order")
    public String showOrder(Model model) {
        if (sessionInfoService.getCart() == null ||
                sessionInfoService.getCart().isEmpty()) {
            return "redirect:/menu";
        }
        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order")
    public String processOrderForm(
            @RequestParam(value = "selectedItemsIds", required = false) List<Long> selectedItemsIds,
            @RequestParam(value = "quantities", required = false) List<Integer> quantities,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (selectedItemsIds == null || selectedItemsIds.isEmpty()) {
            String errorMessage = messageSource.getMessage(
                    "error.please_select_at_least_one_item",
                    null,
                    LocaleContextHolder.getLocale()
            );
            redirectAttributes.addFlashAttribute("error", errorMessage);
            return "redirect:/menu";
        }

        List<MenuItem> selectedMenuItems = new ArrayList<>();
        for (int i = 0; i < selectedItemsIds.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                selectedMenuItems.add(menuItemService.getMenuItemById(selectedItemsIds.get(i)));
            }
        }
        sessionInfoService.setCart(selectedMenuItems);

        model.addAttribute("sessionInfoService", sessionInfoService);
        return "order";
    }

    @PostMapping("/order/submit")
    public String orderSubmit() {
        User user = userService.getUserByEmail(sessionInfoService.getEmail());
        user.setAddress(sessionInfoService.getAddress());
        user.setPhone(sessionInfoService.getPhone());
        user.setName(sessionInfoService.getUsername());

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .itemList(sessionInfoService.getCart())
                .totalPrice(sessionInfoService.getTotalPrice())
                .orderDate(LocalDateTime.now())
                .build();

        Delivery delivery = Delivery.builder()
                .order(order)
                .deliveryTime(LocalDateTime.now())
                .phone(sessionInfoService.getPhone())
                .address(sessionInfoService.getAddress())
                .build();

        orderService.createOrder(delivery.getOrder());
        deliveryService.createDelivery(delivery);

        log.info(delivery.toString());
        log.info(order.toString());
        log.info(user.toString());

        return "redirect:/menu";
    }
}
