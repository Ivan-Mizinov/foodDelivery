package org.example.fooddelivery.data.repoImpls.cassandra;

import org.example.fooddelivery.data.repoImpls.cassandra.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MenuItemCassandraRepository extends CassandraRepository<MenuItemEntity, UUID> {
    @Query("SELECT * FROM menu_items WHERE menu_category=?0 ALLOW FILTERING")
    List<MenuItemEntity> findByMenuCategory(MenuCategory category);

}
