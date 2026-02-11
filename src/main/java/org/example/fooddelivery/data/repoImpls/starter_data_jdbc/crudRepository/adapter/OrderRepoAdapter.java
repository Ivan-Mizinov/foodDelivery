package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.MenuItemRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.OrderRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper.MenuItemMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper.OrderMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("OrderRepoAdapterCrud")
public class OrderRepoAdapter implements OrderRepo {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(@Qualifier("OrderRepoExtCrudRepo") OrderRepository repository,
                            @Qualifier("UserRepoExtCrudRepo") UserRepository userRepo,
                            @Qualifier("MenuItemRepoExtCrudRepo") MenuItemRepository menuItemRepo,
                            OrderMapper mapper, UserMapper userMapper, MenuItemMapper menuItemMapper) {
        this.orderRepository = repository;
        this.userRepository = userRepo;
        this.menuItemRepository = menuItemRepo;
        this.orderMapper = mapper;
        this.userMapper = userMapper;
        this.menuItemMapper = menuItemMapper;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        OrderEntity orderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        order.getItemList().forEach(iMenuItem ->
                orderRepository.insertToOrdersMenuItems(orderEntity.getId(), iMenuItem.getId()));
        return orderMapper.getIOrderFromOrderEntity(orderEntity, order.getUser(), order.getItemList());
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        OrderEntity existingOrderEntity = orderRepository.findById(order.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + order.getId()));
        OrderEntity updatedOrderEntity = orderRepository.save(orderMapper.getOrderEntityFromIOrder(order));
        orderRepository.deleteMenuItemsIdByOrderId(existingOrderEntity.getId());
        order.getItemList().forEach(menuItem -> orderRepository.insertToOrdersMenuItems(updatedOrderEntity.getId(), menuItem.getId()));
        return order;
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        orderRepository.updateOrderStatus(orderId, status);
        return getOrderById(orderId);
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        List<OrderEntity> orderEntities = orderRepository.getByUserId(user.getId());

        return orderEntities.stream().map(orderEntity -> {
            List<IMenuItem> menuItems = orderRepository.getMenuItemsIdsByOrderId(orderEntity.getId())
                    .stream()
                    .map(menuItemId -> menuItemRepository.findById(menuItemId)
                            .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                            .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                    .toList();
            return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
        }).toList();

    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        List<OrderEntity> orderEntities = orderRepository.getByStatus(status);

        return orderEntities.stream().map(orderEntity -> {
            IUser user = userRepository.findById(orderEntity.getUserId())
                    .map(userMapper::getIUserFromUserEntity)
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUserId()));
            List<IMenuItem> menuItems = orderRepository.getMenuItemsIdsByOrderId(orderEntity.getId())
                    .stream()
                    .map(menuItemId -> menuItemRepository.findById(menuItemId)
                            .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                            .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                    .toList();
            return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
        }).toList();
    }

    public IOrder getOrderById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        IUser user = userMapper.getIUserFromUserEntity(userRepository.findById(orderEntity.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + orderEntity.getUserId())));
        List<IMenuItem> menuItems = orderRepository.getMenuItemsIdsByOrderId(orderId).stream()
                .map(menuItemId -> menuItemRepository.findById(menuItemId)
                        .map(menuItemMapper::getIMenuItemFromMenuItemEntity)
                        .orElseThrow(() -> new RuntimeException("Menuitem not found with id: " + menuItemId)))
                .toList();
        return orderMapper.getIOrderFromOrderEntity(orderEntity, user, menuItems);
    }
}
