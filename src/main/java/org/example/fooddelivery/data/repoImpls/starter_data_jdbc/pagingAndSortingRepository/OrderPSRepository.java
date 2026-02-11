package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderPSRepository extends PagingAndSortingRepository<OrderEntity, Long>, OrderRepository {

    @Override
    List<OrderEntity> findAll(Sort sort);

    @Override
    Page<OrderEntity> findAll(Pageable pageable);

    Page<OrderEntity> findByUserId(Long userId, Pageable pageable);
    Page<OrderEntity> findByStatus(OrderStatus status, Pageable pageable);
    List<OrderEntity> findByStatusOrderByOrderDateDesc(OrderStatus status, Sort sort);
    Page<OrderEntity> findByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable);
    Page<OrderEntity> findByTotalPriceGreaterThanEqual(BigDecimal totalPrice, Pageable pageable);
    List<OrderEntity> findByTotalPriceLessThanEqualOrderByOrderDateAsc(BigDecimal totalPrice, Sort sort);

}
