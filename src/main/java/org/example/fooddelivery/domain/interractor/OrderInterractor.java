package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.OrderRepo;

import java.util.List;

public class OrderInterractor {
    private final OrderRepo repo;

    public OrderInterractor(OrderRepo repo) {
        this.repo = repo;
    }

    public IOrder createOrder(IOrder order) {
        return repo.saveOrder(order);
    }

    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        return repo.updateOrderStatus(orderId, status);
    }

    public IOrder changeOrder(IOrder order) {
        return repo.updateOrder(order);
    }

    public List<IOrder> getOrdersByUser(IUser user) {
        return repo.getOrdersByUser(user);
    }

    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        return repo.getOrdersByStatus(status);
    }
}
