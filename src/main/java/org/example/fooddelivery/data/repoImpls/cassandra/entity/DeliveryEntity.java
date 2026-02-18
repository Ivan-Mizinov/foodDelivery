//package org.example.fooddelivery.data.repoImpls.cassandra.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.cassandra.core.mapping.Column;
//import org.springframework.data.cassandra.core.mapping.PrimaryKey;
//import org.springframework.data.cassandra.core.mapping.Table;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Table("deliveries")
//public class DeliveryEntity {
//
//    @PrimaryKey
//    private UUID id = UUID.randomUUID();
//
//    private String address;
//    private String phone;
//
//    @Column("delivery_time")
//    private LocalDateTime deliveryTime;
//
//    @Column("order_id")
//    private UUID orderId;
//}
