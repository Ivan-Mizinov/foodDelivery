package org.example.fooddelivery.data.repoImpls.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Node("Delivery")
public class DeliveryEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    @Relationship(type = "FOR_ORDER", direction = Relationship.Direction.OUTGOING)
    private OrderEntity order;
}
