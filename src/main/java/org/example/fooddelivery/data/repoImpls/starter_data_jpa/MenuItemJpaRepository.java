package org.example.fooddelivery.data.repoImpls.starter_data_jpa;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemJpaRepository extends JpaRepository<MenuItemEntity, Long> {

    List<MenuItemEntity> findByMenuCategory(MenuCategory category);

}
