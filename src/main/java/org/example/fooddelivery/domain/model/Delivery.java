package org.example.fooddelivery.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Delivery {
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    private Order order;
}
