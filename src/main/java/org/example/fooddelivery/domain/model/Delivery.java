package org.example.fooddelivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery implements IDelivery {
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    private IOrder order;
}
