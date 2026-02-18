//package org.example.fooddelivery.data.repoImpls.cassandra;
//
//import org.example.fooddelivery.data.repoImpls.cassandra.entity.OrderEntity;
//import org.example.fooddelivery.domain.model.OrderStatus;
//import org.springframework.data.cassandra.repository.CassandraRepository;
//import org.springframework.data.cassandra.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface OrderCassandraRepository extends CassandraRepository<OrderEntity, UUID> {
//    @Query("SELECT * FROM orders WHERE user_id=?0 ALLOW FILTERING")
//    List<OrderEntity> findByUser(UUID userId);
//
//    @Query("SELECT * FROM orders WHERE status=?0 ALLOW FILTERING")
//    List<OrderEntity> findByStatus(OrderStatus status);
//
//}
