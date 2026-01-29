package org.example.fooddelivery.domain.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class Order {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private IUser user;
    private List<MenuItem> itemList;
    private BigDecimal totalPrice;
}
