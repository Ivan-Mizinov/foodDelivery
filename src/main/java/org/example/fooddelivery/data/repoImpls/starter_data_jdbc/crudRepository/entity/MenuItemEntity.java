package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Table("menu_items")
public class MenuItemEntity implements IMenuItem {
    @Id
    private Long id;
    private String name;
    private MenuCategory menuCategory;
    private BigDecimal price;
}
