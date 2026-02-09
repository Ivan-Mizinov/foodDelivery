package org.example.fooddelivery.data.repoImpls.collectionFramework;

import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MenuItemRepoImpl implements MenuItemRepo {
    private final List<IMenuItem> items = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);
    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        menuItem.setId(nextId.getAndIncrement());
        items.add(menuItem);
        return menuItem;
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        int index = items.indexOf(menuItem);
        if (index != -1) items.set(index, menuItem);
        return menuItem;
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        return items.stream()
                .filter(menuItem -> menuItem.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        return items.stream()
                .filter(menuItem -> menuItem.getMenuCategory().equals(category))
                .toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        items.remove(menuItem);
    }
}
