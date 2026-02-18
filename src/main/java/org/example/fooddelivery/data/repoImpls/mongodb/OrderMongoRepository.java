package org.example.fooddelivery.data.repoImpls.mongodb;

import org.example.fooddelivery.data.repoImpls.mongodb.entity.OrderEntity;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderMongoRepository extends MongoRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID userId);
    List<OrderEntity> findByStatus(OrderStatus status);
}
