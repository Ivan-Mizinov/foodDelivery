package org.example.fooddelivery.data.repoImpls.redis;

import org.example.fooddelivery.data.repoImpls.redis.entity.DeliveryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DeliveryRedisRepository extends CrudRepository<DeliveryEntity, UUID> {}
