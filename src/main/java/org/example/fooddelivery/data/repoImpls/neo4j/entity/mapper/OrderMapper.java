package org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.OrderEntity;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.Order;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("OrderMapper_Neo4j")
public class OrderMapper {
    private final UserMapper userMapper;
    private final MenuItemMapper menuItemMapper;

    public OrderMapper(@Qualifier("UserMapper_Neo4j") UserMapper userMapper,
                       @Qualifier("MenuItemMapper_Neo4j")  MenuItemMapper menuItemMapper) {
        this.userMapper = userMapper;
        this.menuItemMapper = menuItemMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {
        if (iOrder == null) return null;

        return new OrderEntity(
                UUIDUtils.getUUIDFromLong(iOrder.getId()),
                iOrder.getOrderDate(),
                iOrder.getStatus(),
                userMapper.getUserEntityFromIUser(iOrder.getUser()),
                iOrder.getTotalPrice(),
                iOrder.getItemList().stream().map(menuItemMapper::getMenuItemEntityFromIMenuItem).toList()
        );
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity) {
        if (orderEntity == null) return null;
        return new Order(
                UUIDUtils.getLongFromUUID(orderEntity.getId()),
                orderEntity.getOrderDate(),
                orderEntity.getStatus(),
                userMapper.getIUserFromUserEntity(orderEntity.getUser()),
                orderEntity.getItemList().stream().map(menuItemMapper::getIMenuItemFromMenuItemEntity).toList(),
                orderEntity.getTotalPrice()
        );
    }
}
