//package org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper;
//
//import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
//import org.example.fooddelivery.data.repoImpls.cassandra.entity.OrderEntity;
//import org.example.fooddelivery.domain.model.IMenuItem;
//import org.example.fooddelivery.domain.model.IOrder;
//import org.example.fooddelivery.domain.model.IUser;
//import org.example.fooddelivery.domain.model.Order;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component("OrderMapper_Cass")
//public class OrderMapper {
//
//    public OrderEntity getOrderEntityFromIOrder(IOrder iOrder) {
//        if (iOrder == null) return null;
//
//        return new OrderEntity(
//                UUIDUtils.getUUIDFromLong(iOrder.getId()),
//                iOrder.getOrderDate(),
//                iOrder.getStatus(),
//                UUIDUtils.getUUIDFromLong(iOrder.getUser().getId()),
//                iOrder.getTotalPrice(),
//                iOrder.getItemList()
//                        .stream()
//                        .map(item -> UUIDUtils.getUUIDFromLong(item.getId()))
//                        .toList()
//        );
//    }
//
//    public IOrder getIOrderFromOrderEntity(OrderEntity orderEntity, IUser iUser, List<IMenuItem> iMenuItems) {
//        if (orderEntity == null) return null;
//        return new Order(
//                UUIDUtils.getLongFromUUID(orderEntity.getId()),
//                orderEntity.getOrderDate(),
//                orderEntity.getStatus(),
//                iUser,
//                iMenuItems,
//                orderEntity.getTotalPrice()
//        );
//    }
//}
