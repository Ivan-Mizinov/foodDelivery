package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.Order;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.OrderRepo;

import java.util.List;

public class OrderInterractor {
    private final OrderRepo repo;

    public OrderInterractor(OrderRepo repo) {
        this.repo = repo;
    }

    public Order createOrder(Order order){
        return repo.saveOrder(order);
    }

    public Order changeOrder(Order order){
        return repo.updateOrder(order);
    }

    public List<Order> getOrdersByUser(User user){
        return repo.getOrdersByUser(user);
    }

    public List<Order> getOrdersByStatus(OrderStatus status){
        return repo.getOrdersByStatus(status);
    }
}
