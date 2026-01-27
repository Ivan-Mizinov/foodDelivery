package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.Order;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.model.User;

import java.util.List;

public interface OrderRepo {
    Order saveOrder(Order order);
    Order updateOrder(Order order);
    List<Order> getOrdersByUser(User user);
    List<Order> getOrdersByStatus(OrderStatus status);
}
