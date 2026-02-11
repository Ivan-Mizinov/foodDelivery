package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.MenuItemMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.OrderMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.UserMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.MenuItemPSRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.OrderPSRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component("OrderRepoAdapterPS")
public class OrderRepoAdapter implements OrderRepo {
    private final OrderPSRepository orderRepository;
    private final UserPSRepository userRepository;
    private final MenuItemPSRepository menuItemRepository;

    private final OrderMapper orderMapper;
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderRepoAdapter(OrderPSRepository orderRepository, UserPSRepository userRepository, MenuItemPSRepository menuItemRepository,
                            OrderMapper orderMapper, UserMapper userMapper, MenuItemMapper menuItemMapper) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderMapper = orderMapper;
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
        Sort sort = Sort.by(Sort.Direction.DESC, "order_date");
        return getOrdersByStatusOrderByOrderDateDesc(status, sort);
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

    public List<IOrder> getAllOrders(Sort sort) {
        return orderRepository.findAll(sort).stream()
                .map(orderEntity -> getOrderById(orderEntity.getId())).toList();
    }

    public Page<IOrder> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderEntity -> getOrderById(orderEntity.getId()));
    }

    public Page<IOrder> getOrdersByUserId(Long userId, Pageable pageable) {
        return orderRepository.findByUserId(userId, pageable)
                .map(orderEntity -> getOrderById(orderEntity.getId()));
    }

    public Page<IOrder> getOrdersByStatus(OrderStatus status, Pageable pageable) {
        return orderRepository.findByStatus(status, pageable)
                .map(orderEntity -> getOrderById(orderEntity.getId()));
    }

    public List<IOrder> getOrdersByStatusOrderByOrderDateDesc(OrderStatus status, Sort sort) {
        return orderRepository.findByStatusOrderByOrderDateDesc(status, sort).stream()
                .map(orderEntity -> getOrderById(orderEntity.getId())).toList();
    }

    public Page<IOrder> getOrdersByUserIdAndStatus(Long userId, OrderStatus status, Pageable pageable) {
        return orderRepository.findByUserIdAndStatus(userId, status, pageable)
                .map(orderEntity -> getOrderById(orderEntity.getId()));
    }

    public Page<IOrder> getOrdersByTotalPriceGreaterThanEqual(BigDecimal totalPrice, Pageable pageable) {
        return orderRepository.findByTotalPriceGreaterThanEqual(totalPrice, pageable)
                .map(orderEntity -> getOrderById(orderEntity.getId()));
    }

    public List<IOrder> getOrdersByTotalPriceLessThanEqualOrderByOrderDateAsc(BigDecimal totalPrice, Sort sort) {
        return orderRepository.findByTotalPriceLessThanEqualOrderByOrderDateAsc(totalPrice, sort).stream()
                .map(orderEntity -> getOrderById(orderEntity.getId())).toList();
    }
}
