package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.IDelivery;

public interface DeliveryRepo {
    IDelivery saveDelivery(IDelivery delivery);
    IDelivery updateDelivery(IDelivery delivery);
    IDelivery getDeliveryById(Long id);
}
