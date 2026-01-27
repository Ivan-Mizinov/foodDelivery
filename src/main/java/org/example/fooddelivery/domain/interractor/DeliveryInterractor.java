package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;

public class DeliveryInterractor {
    private final DeliveryRepo repo;

    public DeliveryInterractor(DeliveryRepo repo) {
        this.repo = repo;
    }

    public Delivery createDelivery(Delivery delivery) {
        return repo.saveDelivery(delivery);
    }

    public Delivery changeDelivery(Delivery delivery) {
        return repo.updateDelivery(delivery);
    }

    public Delivery getDeliveryById(Long id) {
        return repo.getDeliveryById(id);
    }
}
