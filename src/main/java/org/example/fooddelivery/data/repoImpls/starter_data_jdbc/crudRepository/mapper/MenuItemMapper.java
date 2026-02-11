package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class MenuItemMapper {

    private final ModelMapper modelMapper;

    public MenuItemMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public MenuItemEntity getMenuItemEntityFromIMenuItem(IMenuItem menuItem) {
        if (menuItem == null) return null;
        return modelMapper.map(menuItem, MenuItemEntity.class);
    }

    public IMenuItem getIMenuItemFromMenuItemEntity(MenuItemEntity menuItemEntity) {
        if (menuItemEntity == null) return null;
        return modelMapper.map(menuItemEntity, IMenuItem.class);
    }
}
