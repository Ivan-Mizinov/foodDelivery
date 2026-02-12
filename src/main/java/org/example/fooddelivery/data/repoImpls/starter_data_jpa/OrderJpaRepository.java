package org.example.fooddelivery.data.repoImpls.starter_data_jpa;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUser(UserEntity user);

    List<OrderEntity> findByStatusOrderByOrderDateDesc(OrderStatus status);

}
