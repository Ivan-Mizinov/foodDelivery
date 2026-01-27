package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.Delivery;

public interface DeliveryRepo {
    Delivery saveDelivery(Delivery delivery);
    Delivery updateDelivery(Delivery delivery);
    Delivery getDeliveryById(Long id);
}
