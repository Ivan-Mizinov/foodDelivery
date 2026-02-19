package org.example.fooddelivery.data.repoImpls.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node("Order")
public class OrderEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime orderDate;
    private OrderStatus status;
    @Relationship(type = "ORDERED_BY", direction = Relationship.Direction.INCOMING)
    private UserEntity user;
    private BigDecimal totalPrice;
    @Relationship(type = "CONTAINS", direction = Relationship.Direction.OUTGOING)
    private List<MenuItemEntity> itemList;
//    @Relationship(type = "HAS_DELIVERY", direction = Relationship.Direction.OUTGOING)
//    private DeliveryEntity delivery;
}
