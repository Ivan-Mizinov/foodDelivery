package org.example.fooddelivery.data.repoImpls.redis.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.redis.MenuItemRedisRepository;
import org.example.fooddelivery.data.repoImpls.redis.entity.mapper.MenuItemMapper;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("MenuItemRepoAdapter_Redis")
public class MenuItemRepoAdapter implements MenuItemRepo {
    private final MenuItemRedisRepository repository;
    private final MenuItemMapper mapper;

    public MenuItemRepoAdapter(MenuItemRedisRepository repository,
                               @Qualifier("MenuItemMapper_Redis") MenuItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
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
        return repository.findById(UUIDUtils.getUUIDFromLong(id))
                .map(mapper::getIMenuItemFromMenuItemEntity).orElse(null);
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
