package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Table("orders")
public class OrderEntity {
    @Id
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Long userId;
    private BigDecimal totalPrice;
}
