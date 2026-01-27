package org.example.fooddelivery.domain.model;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Order {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private User user;
    private List<MenuItem> items;
    private BigDecimal totalPrice;
}
