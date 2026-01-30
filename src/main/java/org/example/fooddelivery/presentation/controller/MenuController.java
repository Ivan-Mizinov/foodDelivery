package org.example.fooddelivery.presentation.controller;

import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.presentation.service.MenuItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/menu")
public class MenuController {
    private final MenuItemService service;

    public MenuController(MenuItemService service) {
        this.service = service;
    }

    @GetMapping
    public String showMenu(Model model) {
        Map<String, List<IMenuItem>> menuItemsByCategory = Map.of(
                "menu.main_menu", service.getMenuItemsByCategory(MenuCategory.MainMenu),
                "menu.drinks_menu", service.getMenuItemsByCategory(MenuCategory.DrinksMenu),
                "menu.sauces_menu", service.getMenuItemsByCategory(MenuCategory.SaucesMenu));
        model.addAttribute("menuItemsByCategory", menuItemsByCategory);
        return "menu";
    }
}
