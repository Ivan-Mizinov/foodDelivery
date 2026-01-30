package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.*;

import java.util.List;

public interface OrderRepo {
    IOrder saveOrder(IOrder order);
    IOrder updateOrder(IOrder order);
    IOrder updateOrderStatus(Long orderId, OrderStatus status);
    List<IOrder> getOrdersByUser(IUser user);
    List<IOrder> getOrdersByStatus(OrderStatus status);
}
