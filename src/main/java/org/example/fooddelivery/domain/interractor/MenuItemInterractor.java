package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;

import java.util.List;

public class MenuItemInterractor {
    private final MenuItemRepo repo;

    public MenuItemInterractor(MenuItemRepo repo) {
        this.repo = repo;
    }

    public MenuItem saveMenuItem(MenuItem menuItem){
        return repo.saveMenuItem(menuItem);
    }

    public MenuItem updateMenuItem(MenuItem menuItem){
        return repo.updateMenuItem(menuItem);
    }

    public List<MenuItem> getMenuItemsByCategory(MenuCategory category){
        return repo.getMenuItemsByCategory(category);
    }

    public MenuItem getMenuItemById(Long id){
        return repo.getMenuItemById(id);
    }
}
