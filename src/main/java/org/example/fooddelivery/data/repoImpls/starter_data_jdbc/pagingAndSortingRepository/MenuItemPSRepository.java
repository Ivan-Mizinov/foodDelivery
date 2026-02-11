package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.MenuItemEntity;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.math.BigDecimal;
import java.util.List;

public interface MenuItemPSRepository extends PagingAndSortingRepository<MenuItemEntity, Long>, MenuItemRepository {

    @Override
    List<MenuItemEntity> findAll(Sort sort);

    @Override
    Page<MenuItemEntity> findAll(Pageable pageable);

    Page<MenuItemEntity> findByMenuCategory(MenuCategory category, Pageable pageable);
    List<MenuItemEntity> findByMenuCategoryOrderByPriceAsc(MenuCategory category, Sort sort);
    Page<MenuItemEntity> findByPriceLessThanEqual(BigDecimal price, Pageable pageable);
    List<MenuItemEntity> findByPriceGreaterThanEqualOrderByNameAsc(BigDecimal price, Sort sort);
}
