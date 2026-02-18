package org.example.fooddelivery.data.repoImpls.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("deliveries")
public class DeliveryEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    private UUID orderId;
}
