package org.example.fooddelivery.data.repoImpls.starter_data_jpa;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryJpaRepository extends JpaRepository<DeliveryEntity, Long> {}
