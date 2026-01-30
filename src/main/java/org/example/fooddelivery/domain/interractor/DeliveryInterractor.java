package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;

public class DeliveryInterractor {
    private final DeliveryRepo repo;

    public DeliveryInterractor(DeliveryRepo repo) {
        this.repo = repo;
    }

    public IDelivery createDelivery(IDelivery delivery) {
        return repo.saveDelivery(delivery);
    }

    public IDelivery changeDelivery(IDelivery delivery) {
        return repo.updateDelivery(delivery);
    }

    public IDelivery getDeliveryById(Long id) {
        return repo.getDeliveryById(id);
    }
}
