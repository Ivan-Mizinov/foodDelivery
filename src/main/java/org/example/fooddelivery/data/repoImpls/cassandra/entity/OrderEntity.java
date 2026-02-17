package org.example.fooddelivery.data.repoImpls.cassandra.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("orders")
public class OrderEntity {
    @PrimaryKey
    private UUID id = UUID.randomUUID();

    @Column("order_date")
    private LocalDateTime orderDate;

    private OrderStatus status;

    @Column("user_id")
    private UUID userId;

    @Column("total_price")
    private BigDecimal totalPrice;

    @Column("menu_items")
    private List<UUID> menuItemIds;
}
