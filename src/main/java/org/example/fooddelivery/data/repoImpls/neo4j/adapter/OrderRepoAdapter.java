package org.example.fooddelivery.data.repoImpls.neo4j.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.neo4j.OrderNeo4jRepository;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper.OrderMapper;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrderRepoAdapter_Neo4j")
public class OrderRepoAdapter implements OrderRepo {
    private final OrderNeo4jRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderRepoAdapter(OrderNeo4jRepository orderRepository,
                            @Qualifier("OrderMapper_Neo4j") OrderMapper orderMapper,
                            @Qualifier("UserMapper_Neo4j") UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        return orderMapper.getIOrderFromOrderEntity(
                orderRepository.save(orderMapper.getOrderEntityFromIOrder(order)));
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        return saveOrder(order);
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity orderEntity = orderRepository.findById(UUIDUtils.getUUIDFromLong(orderId))
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        orderEntity.setStatus(status);
        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(orderEntity));
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        return orderRepository.findByUser(userMapper.getUserEntityFromIUser(user)).stream()
                .map(orderMapper::getIOrderFromOrderEntity).toList();
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::getIOrderFromOrderEntity).toList();
    }
}
