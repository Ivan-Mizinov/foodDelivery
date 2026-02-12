package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//@Component
public class OrderMapper {

    private final ModelMapper modelMapper;

    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderEntity getOrderEntityFromIOrder(IOrder order) {
        if (order == null) return null;
        return modelMapper.map(order, OrderEntity.class);
    }

    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity) {
        if (orderEntity == null) return null;
        return modelMapper.map(orderEntity, IOrder.class);
    }
}
