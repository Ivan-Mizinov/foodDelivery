package org.example.fooddelivery.data.repoImpls.mongodb;

import org.example.fooddelivery.data.repoImpls.mongodb.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemMongoRepository extends MongoRepository<MenuItemEntity, UUID> {
    List<MenuItemEntity> findByMenuCategory(MenuCategory category);
}
