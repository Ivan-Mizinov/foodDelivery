package org.example.fooddelivery.data.repoImpls.collectionFramework;

import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;

import java.util.ArrayList;
import java.util.List;

public class MenuItemRepoImpl implements MenuItemRepo {
    private final List<MenuItem> items = new ArrayList<>();
    @Override
    public MenuItem saveMenuItem(MenuItem menuItem) {
        items.add(menuItem);
        return menuItem;
    }

    @Override
    public MenuItem updateMenuItem(MenuItem menuItem) {
        int index = items.indexOf(menuItem);
        if (index != -1) items.set(index, menuItem);
        return menuItem;
    }

    @Override
    public MenuItem getMenuItemById(Long id) {
        return items.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<MenuItem> getMenuItemsByCategory(MenuCategory category) {
        return items.stream()
                .filter(menuItem -> menuItem.getCategory().equals(category))
                .toList();
    }

    @Override
    public void deleteMenuItem(MenuItem menuItem) {
        items.remove(menuItem);
    }
}
