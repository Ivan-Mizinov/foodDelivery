package org.example.fooddelivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuItem implements IMenuItem {
    private Long id;
    private String name;
    private MenuCategory category;
    private BigDecimal price;
}
