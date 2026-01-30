package org.example.fooddelivery.domain.model;

import java.math.BigDecimal;

public interface IMenuItem {
    Long getId();
    void setId(Long id);
    String getName();
    void setName(String name);
    MenuCategory getCategory();
    void setCategory(MenuCategory category);
    BigDecimal getPrice();
    void setPrice(BigDecimal price);
}
