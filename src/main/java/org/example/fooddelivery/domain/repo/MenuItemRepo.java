package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;

import java.util.List;

public interface MenuItemRepo {
    MenuItem saveMenuItem(MenuItem menuItem);
    MenuItem updateMenuItem(MenuItem menuItem);
    MenuItem getMenuItemById(Long id);
    List<MenuItem> getMenuItemsByCategory(MenuCategory category);
    void deleteMenuItem(MenuItem menuItem);
}
