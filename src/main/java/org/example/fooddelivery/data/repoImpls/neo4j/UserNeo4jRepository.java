package org.example.fooddelivery.data.repoImpls.neo4j;

import org.example.fooddelivery.data.repoImpls.neo4j.entity.UserEntity;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserNeo4jRepository extends Neo4jRepository<UserEntity, UUID> {
    UserEntity findByEmail(String email);
}
