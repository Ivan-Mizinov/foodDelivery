package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("OrderMapper_JPA")
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder order) {
        if (order == null) return null;
        OrderEntity orderEntity = modelMapper.map(order, OrderEntity.class);
        orderEntity.setUser((UserEntity) order.getUser());
        return orderEntity;
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity) {
        if (orderEntity == null) return null;
        IOrder order = modelMapper.map(orderEntity, Order.class);
        order.setUser(orderEntity.getUser());
        return order;
    }
}
