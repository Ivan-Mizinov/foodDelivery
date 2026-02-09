package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.model.IOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Table("deliveries")
public class DeliveryEntity implements IDelivery {
    @Id
    private Long id;
    private String address;
    private String phone;
    private LocalDateTime deliveryTime;
    private IOrder order;
}
