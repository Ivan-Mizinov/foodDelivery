package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;

import java.util.List;

public interface MenuItemRepo {
    IMenuItem saveMenuItem(IMenuItem menuItem);
    IMenuItem updateMenuItem(IMenuItem menuItem);
    IMenuItem getMenuItemById(Long id);
    List<IMenuItem> getMenuItemsByCategory(MenuCategory category);
    void deleteMenuItem(IMenuItem menuItem);
}
