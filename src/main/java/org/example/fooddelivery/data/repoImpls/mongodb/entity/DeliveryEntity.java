//package org.example.fooddelivery.data.repoImpls.mongodb.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.mongodb.core.mapping.Document;
//
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Document(collection = "deliveries")
//public class DeliveryEntity {
//
//    @Id
//    private UUID id = UUID.randomUUID();
//    private String address;
//    private String phone;
//    private LocalDateTime deliveryTime;
//    private UUID orderId;
//}
