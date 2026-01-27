package org.example.fooddelivery.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Delivery {
    private Long id;
    private String address;
    private LocalDateTime deliveryTime;
    private Order order;
}
