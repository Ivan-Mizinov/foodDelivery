package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public OrderEntity getOrderEntityFromIOrder(IOrder order) {

        return OrderEntity.builder()
                .id(order.getId())
                .orderDate(order.getOrderDate())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .totalPrice(order.getTotalPrice())
                .build();
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity,
                                           IUser user,
                                           List<IMenuItem> menuItems) {
        return Order.builder()
                .id(orderEntity.getId())
                .orderDate(orderEntity.getOrderDate())
                .status(orderEntity.getStatus())
                .user(user)
                .itemList(menuItems)
                .totalPrice(orderEntity.getTotalPrice())
                .build();
    }
}
