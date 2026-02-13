package org.example.fooddelivery.data.repoImpls.starter_data_jpa.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.MenuItemJpaRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper.MenuItemMapper;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("MenuItemRepoAdapter_JPA")
public class MenuItemRepoAdapter implements MenuItemRepo {
    private final MenuItemJpaRepository repository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(MenuItemJpaRepository repository,
                               @Qualifier("MenuItemMapper_JPA") MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        return mapper.getIMenuItemFromMenuItemEntity(
                repository.save(
                        mapper.getMenuItemEntityFromIMenuItem(menuItem))
        );
    }

    @Transactional
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
        return repository.findByMenuCategory(category)
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
