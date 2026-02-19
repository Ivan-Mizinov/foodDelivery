package org.example.fooddelivery.data.repoImpls.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node("User")
public class UserEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;
    @Relationship(type = "PLACED_ORDERS", direction = Relationship.Direction.OUTGOING)
    private List<OrderEntity> orders = new ArrayList<>();
}
