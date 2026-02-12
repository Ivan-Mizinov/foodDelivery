package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "deliveries")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false, name = "delivery_time")
    private LocalDateTime deliveryTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, name = "order_id")
    private OrderEntity order;
}
