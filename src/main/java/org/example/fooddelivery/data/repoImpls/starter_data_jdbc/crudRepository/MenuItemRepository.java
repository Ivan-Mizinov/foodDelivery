package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository("MenuItemRepoExtCrudRepo")
public interface MenuItemRepository extends CrudRepository<MenuItemEntity, Long> {
    List<MenuItemEntity> getMenuItemsByMenuCategory(MenuCategory menuCategory);

    @Modifying
    @Query("INSERT INTO menu_items(id, name, menu_category, price) VALUES(:id, :name, :menu_category, :price)")
    void insert(@Param("id") Long id,
                @Param("name") String name,
                @Param("menu_category") MenuCategory menuCategory,
                @Param("price") BigDecimal price);

    @Query("SELECT * FROM orders_menu_items WHERE order_id = :order_id")
    List<MenuItemEntity> getMenuItemsByOrderId(@Param("order_id") Long orderId);
}
