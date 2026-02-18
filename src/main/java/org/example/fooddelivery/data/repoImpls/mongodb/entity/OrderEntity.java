package org.example.fooddelivery.data.repoImpls.mongodb.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private LocalDateTime orderDate;
    private OrderStatus status;
    private UUID userId;
    private BigDecimal totalPrice;
    private List<UUID> menuItemIds;
}
