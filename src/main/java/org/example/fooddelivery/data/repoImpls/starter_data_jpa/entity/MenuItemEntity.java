package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "menu_items")
public class MenuItemEntity extends BaseEntity implements IMenuItem {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "menu_category")
    private MenuCategory menuCategory;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
