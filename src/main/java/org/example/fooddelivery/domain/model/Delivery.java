package org.example.fooddelivery.domain.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Delivery implements IDelivery {
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    private IOrder order;
}
