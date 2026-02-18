package org.example.fooddelivery.data.repoImpls.redis;

import org.example.fooddelivery.data.repoImpls.redis.entity.OrderEntity;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRedisRepository extends CrudRepository<OrderEntity, UUID> {
    List<OrderEntity> findByUserId(UUID userId);
    List<OrderEntity> findByStatus(OrderStatus status);
}
