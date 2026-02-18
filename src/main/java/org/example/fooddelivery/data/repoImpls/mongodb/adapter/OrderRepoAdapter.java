package org.example.fooddelivery.data.repoImpls.mongodb.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.mongodb.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.mongodb.entity.mapper.MenuItemMapper;
import org.example.fooddelivery.data.repoImpls.mongodb.entity.mapper.OrderMapper;
import org.example.fooddelivery.data.repoImpls.mongodb.entity.mapper.UserMapper;
import org.example.fooddelivery.data.repoImpls.mongodb.MenuItemMongoRepository;
import org.example.fooddelivery.data.repoImpls.mongodb.OrderMongoRepository;
import org.example.fooddelivery.data.repoImpls.mongodb.UserMongoRepository;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Component("OrderRepoAdapter_MongoDB")
public class OrderRepoAdapter implements OrderRepo {
    private final OrderMongoRepository orderRepository;
    private final MenuItemMongoRepository menuItemRepository;
    private final UserMongoRepository userRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(OrderMongoRepository orderRepository,
                            MenuItemMongoRepository menuItemRepository,
                            UserMongoRepository userRepository,
                            @Qualifier("OrderMapper_MongoDB") OrderMapper orderMapper,
                            @Qualifier("UserMapper_MongoDB") UserMapper userMapper,
                            @Qualifier("MenuItemMapper_MongoDB") MenuItemMapper menuItemMapper) {
        this.orderRepository = orderRepository;
        this.menuItemRepository = menuItemRepository;
        this.userRepository = userRepository;
        this.orderMapper = orderMapper;
        this.userMapper = userMapper;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        OrderEntity orderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        return getOrderById(orderEntity.getId());
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        if (!orderRepository.existsById(UUIDUtils.getUUIDFromLong(order.getId()))) {
            throw new RuntimeException("Order not found with id: " + order.getId());
        }

        OrderEntity orderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        return getOrderById(orderEntity.getId());
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        OrderEntity orderEntity = getOrderEntityByUUID(UUIDUtils.getUUIDFromLong(orderId));

        orderEntity.setStatus(status);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        return getOrderById(savedOrder.getId());
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        UUID userId = UUIDUtils.getUUIDFromLong(user.getId());
        return orderRepository.findByUserId(userId)
                .stream()
                .map(entity -> getOrderById(entity.getId()))
                .toList();
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status)
                .stream()
                .sorted(Comparator.comparing(OrderEntity::getOrderDate).reversed())
                .map(entity -> getOrderById(entity.getId()))
                .toList();
    }

    public IOrder getOrderById(UUID orderId) {
        OrderEntity orderEntity = getOrderEntityByUUID(orderId);

        IUser iUser = userMapper.getIUserFromUserEntity(userRepository.findById(orderEntity.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUserId())));

        List<IMenuItem> iMenuItems = orderEntity.getMenuItemIds().stream()
                .map(menuItemId ->
                        menuItemRepository.findById(menuItemId)
                                .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                                .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                .toList();
        return orderMapper.getIOrderFromOrderEntity(orderEntity, iUser, iMenuItems);
    }

    private OrderEntity getOrderEntityByUUID(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
    }
}
