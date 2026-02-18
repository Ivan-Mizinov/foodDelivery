package org.example.fooddelivery.data.repoImpls.redis;

import org.example.fooddelivery.data.repoImpls.redis.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRedisRepository extends CrudRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
