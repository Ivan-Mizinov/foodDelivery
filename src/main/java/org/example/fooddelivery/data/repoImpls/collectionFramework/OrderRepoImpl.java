package org.example.fooddelivery.data.repoImpls.collectionFramework;

import org.example.fooddelivery.domain.model.Order;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepoImpl implements OrderRepo {
    private final List<Order> orders=new ArrayList<>();

    @Override
    public Order saveOrder(Order order) {
        orders.add(order);
        return order;
    }

    @Override
    public Order updateOrder(Order order) {
        int index=orders.indexOf(order);
        if (index != -1) orders.set(index, order);
        return order;
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orders.stream()
                     .filter(order -> order.getUser().getId().equals(user.getId()))
                     .toList();
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus orderStatus) {
        return orders.stream()
                     .filter(order -> order.getStatus().name().equals(orderStatus.name()))
                     .toList();
    }
}
