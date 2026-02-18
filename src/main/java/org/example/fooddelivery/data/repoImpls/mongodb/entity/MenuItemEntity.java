package org.example.fooddelivery.data.repoImpls.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "menu_items")
public class MenuItemEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private MenuCategory menuCategory;
    private BigDecimal price;
}
