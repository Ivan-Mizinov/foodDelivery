package org.example.fooddelivery.data.repoImpls.redis.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.redis.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuItem;
import org.springframework.stereotype.Component;

@Component("MenuItemMapper_Redis")
public class MenuItemMapper {

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem) {
        if (iMenuItem == null) return null;
        MenuItemEntity entity = new MenuItemEntity();
        entity.setId(UUIDUtils.getUUIDFromLong(iMenuItem.getId()));
        entity.setName(iMenuItem.getName());
        entity.setMenuCategory(iMenuItem.getMenuCategory());
        entity.setPrice(iMenuItem.getPrice());
        return entity;
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        IMenuItem menuItem = new MenuItem();
        menuItem.setId(UUIDUtils.getLongFromUUID(menuItemEntity.getId()));
        menuItem.setName(menuItemEntity.getName());
        menuItem.setMenuCategory(menuItemEntity.getMenuCategory());
        menuItem.setPrice(menuItemEntity.getPrice());
        return menuItem;
    }
}
