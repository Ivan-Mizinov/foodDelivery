package org.example.fooddelivery.data.repoImpls.redis;

import org.example.fooddelivery.data.repoImpls.redis.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemRedisRepository extends CrudRepository<MenuItemEntity, UUID> {
    List<MenuItemEntity> findByMenuCategory(MenuCategory category);
}
