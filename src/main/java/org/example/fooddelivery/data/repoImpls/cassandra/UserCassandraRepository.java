package org.example.fooddelivery.data.repoImpls.cassandra;

import org.example.fooddelivery.data.repoImpls.cassandra.entity.UserEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserCassandraRepository extends CassandraRepository<UserEntity, UUID> {
    @Query("SELECT * FROM users WHERE email=?0 ALLOW FILTERING")
    UserEntity findByEmail(String email);
}
