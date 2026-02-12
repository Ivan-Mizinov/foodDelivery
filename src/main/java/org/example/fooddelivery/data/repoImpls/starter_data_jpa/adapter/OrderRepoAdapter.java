package org.example.fooddelivery.data.repoImpls.starter_data_jpa.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.MenuItemJpaRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.MenuItemEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper.OrderMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper.UserMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.OrderJpaRepository;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component("OrderRepoAdapter_JPA")
public class OrderRepoAdapter implements OrderRepo {
    private final OrderJpaRepository orderRepository;
    private final MenuItemJpaRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;

    public OrderRepoAdapter(OrderJpaRepository orderRepository, MenuItemJpaRepository menuItemRepository,
                            @Qualifier("OrderMapper_JPA") OrderMapper orderMapper,
                            @Qualifier("UserMapper_JPA") UserMapper userMapper) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
    }

    @Transactional
    @Override
    public IOrder saveOrder(IOrder order) {
        OrderEntity orderEntity = orderMapper.getOrderEntityFromIOrder(order);
        List<MenuItemEntity> itemList = orderEntity.getItemList().stream()
                .map(menuItemEntity -> menuItemRepository.findById(menuItemEntity.getId())
                        .orElseThrow(() -> new RuntimeException("MenuItem not found with id: " + menuItemEntity.getId())))
                .toList();
        orderEntity.setItemList(itemList);
        return orderMapper.getIOrderFromOrderEntity(orderRepository.save(orderEntity));
    }

    @Transactional
    @Override
    public IOrder updateOrder(IOrder order) {
        if (!orderRepository.existsById(order.getId())) {
            throw new RuntimeException("Order not found with id: " + order.getId());
        }

        return orderMapper.getIOrderFromOrderEntity(
                orderRepository.save(
                        orderMapper.getOrderEntityFromIOrder(order)));
    }

    @Transactional
    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
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
        return orderRepository.findByStatusOrderByOrderDateDesc(status).stream()
                .map(orderMapper::getIOrderFromOrderEntity).toList();
    }
}
