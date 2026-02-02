package org.example.fooddelivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data @AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order implements IOrder {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private IUser user;
    private List<IMenuItem> itemList;
    private BigDecimal totalPrice;
}
