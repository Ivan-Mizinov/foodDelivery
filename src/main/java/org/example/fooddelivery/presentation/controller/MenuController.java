package org.example.fooddelivery.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.presentation.service.MenuItemService;
import org.example.fooddelivery.presentation.service.SessionInfoService;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/menu")
public class MenuController {
    private final MenuItemService menuItemService;
    private final SessionInfoService sessionInfoService;
    private final MessageSource messageSource;

    @GetMapping
    public String showMenu(Model model) {
        Map<String, List<IMenuItem>> menuItemsByCategory = Map.of(
                "menu.main_menu", menuItemService.getMenuItemsByCategory(MenuCategory.MainMenu),
                "menu.drinks_menu", menuItemService.getMenuItemsByCategory(MenuCategory.DrinksMenu),
                "menu.sauces_menu", menuItemService.getMenuItemsByCategory(MenuCategory.SaucesMenu));
        model.addAttribute("menuItemsByCategory", menuItemsByCategory);
        return "menu";
    }

    @PostMapping("/order")
    public String processOrderForm(
            @RequestParam(value = "selectedItemsIds", required = false) List<Long> selectedItemsIds,
            @RequestParam(value = "quantities", required = false) List<Integer> quantities,
            Model model
    ) {
        if (selectedItemsIds == null || selectedItemsIds.isEmpty()) {
            String errorMessage = messageSource.getMessage(
                    "error.please_select_at_least_one_item",
                    null,
                    LocaleContextHolder.getLocale()
            );
            model.addAttribute("error", errorMessage);
            return showMenu(model);
        }

        List<IMenuItem> selectedMenuItems = new ArrayList<>();
        for (int i = 0; i < selectedItemsIds.size(); i++) {
            for (int j = 0; j < quantities.get(i); j++) {
                selectedMenuItems.add(menuItemService.getMenuItemById(selectedItemsIds.get(i)));
            }
        }
        sessionInfoService.setCart(selectedMenuItems);
        return "redirect:/order";
    }
}
