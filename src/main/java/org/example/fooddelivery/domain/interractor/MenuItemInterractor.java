package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.repo.MenuItemRepo;

import java.util.List;

public class MenuItemInterractor {
    private final MenuItemRepo repo;

    public MenuItemInterractor(MenuItemRepo repo) {
        this.repo = repo;
    }

    public IMenuItem saveMenuItem(IMenuItem menuItem){
        return repo.saveMenuItem(menuItem);
    }

    public IMenuItem updateMenuItem(IMenuItem menuItem){
        return repo.updateMenuItem(menuItem);
    }

    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category){
        return repo.getMenuItemsByCategory(category);
    }

    public IMenuItem getMenuItemById(Long id){
        return repo.getMenuItemById(id);
    }
}
