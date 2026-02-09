package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.MenuItemEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper.MenuItemMapper;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component("MenuItemRepoAdapterCrud")
public class MenuItemRepoAdapter implements MenuItemRepo {
    private final MenuItemRepository repository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(@Qualifier("MenuItemRepoExtCrudRepo") MenuItemRepository repository,
                               MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        if (menuItem.getId() != null) {
            Optional<MenuItemEntity> optionalMenuItem = repository.findById(menuItem.getId());
            if (optionalMenuItem.isPresent()) {
                return mapper.getIMenuItemFromMenuItemEntity(
                        repository.save(
                                mapper.getMenuItemEntityFromIMenuItem(menuItem))
                );
            } else {
                repository.insert(
                        menuItem.getId(), menuItem.getName(), menuItem.getMenuCategory(), menuItem.getPrice());
            }
        }

        return mapper.getIMenuItemFromMenuItemEntity(
                repository.save(
                        mapper.getMenuItemEntityFromIMenuItem(menuItem))
        );
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        return saveMenuItem(menuItem);
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        return repository.findById(id)
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .orElse(null);
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        return repository.getMenuItemsByMenuCategory(category)
                .stream()
                .map(mapper::getIMenuItemFromMenuItemEntity)
                .toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        repository.delete(
                mapper.getMenuItemEntityFromIMenuItem(menuItem)
        );
    }
}
