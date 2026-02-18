//package org.example.fooddelivery.data.repoImpls.cassandra.entity;
//
//import lombok.*;
//import org.example.fooddelivery.domain.model.MenuCategory;
//import org.springframework.data.cassandra.core.mapping.Column;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table("menu_items")
//public class MenuItemEntity {
//    @PrimaryKey
//    private UUID id = UUID.randomUUID();
//
//    private String name;
//
//    @Column("menu_category")
//    private MenuCategory menuCategory;
//
//    private BigDecimal price;
//}
