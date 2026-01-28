package org.example.fooddelivery.presentation.service;

import org.example.fooddelivery.domain.interractor.OrderInterractor;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.stereotype.Service;

@Service
public class OrderService extends OrderInterractor {
    public OrderService(OrderRepo repo) {
        super(repo);
    }
}
