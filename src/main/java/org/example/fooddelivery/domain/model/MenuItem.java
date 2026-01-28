package org.example.fooddelivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class MenuItem {
    private Long id;
    private String name;
    private MenuCategory category;
    private BigDecimal price;
}
