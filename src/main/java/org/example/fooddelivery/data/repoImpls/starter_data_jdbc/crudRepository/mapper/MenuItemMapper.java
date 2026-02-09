package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuItem;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {
    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem menuItem) {
        return MenuItemEntity.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .menuCategory(menuItem.getMenuCategory())
                .price(menuItem.getPrice())
                .build();
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        return MenuItem.builder()
                .id(menuItemEntity.getId())
                .name(menuItemEntity.getName())
                .price(menuItemEntity.getPrice())
                .menuCategory(menuItemEntity.getMenuCategory())
                .build();

    }
}
