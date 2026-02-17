package org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("MenuItemMapper_Cass")
public class MenuItemMapper {

    private final ModelMapper modelMapper;

    public MenuItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem iMenuItem) {
        if (iMenuItem == null) return null;
        MenuItemEntity menuItemEntity = modelMapper.map(iMenuItem, MenuItemEntity.class);
        menuItemEntity.setId(UUIDUtils.getUUIDFromLong(iMenuItem.getId()));
        return menuItemEntity;
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        IMenuItem iMenuItem = modelMapper.map(menuItemEntity, MenuItem.class);
        iMenuItem.setId(UUIDUtils.getLongFromUUID(menuItemEntity.getId()));
        return iMenuItem;
    }
}
