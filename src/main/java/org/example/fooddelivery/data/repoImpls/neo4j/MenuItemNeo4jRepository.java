package org.example.fooddelivery.data.repoImpls.neo4j;

import org.example.fooddelivery.data.repoImpls.neo4j.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemNeo4jRepository extends Neo4jRepository<MenuItemEntity, UUID> {
    List<MenuItemEntity> findByMenuCategory(MenuCategory category);
}
