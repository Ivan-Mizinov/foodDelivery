package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder order) {
        if (order == null) return null;
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderEntity.setUserId(order.getUser().getId());
        return orderEntity;
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity,
                                           IUser user,
                                           List<IMenuItem> menuItems) {
        if (orderEntity == null) return null;
        IOrder order = modelMapper.map(orderEntity, Order.class);
        order.setUser(user);
        order.setItemList(menuItems);
        return order;
    }
}
