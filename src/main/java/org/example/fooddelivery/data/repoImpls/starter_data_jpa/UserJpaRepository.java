package org.example.fooddelivery.data.repoImpls.starter_data_jpa;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmail(String email);
}
