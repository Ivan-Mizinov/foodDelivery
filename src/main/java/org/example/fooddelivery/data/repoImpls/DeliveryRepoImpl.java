package org.example.fooddelivery.data.repoImpls;

import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;

import java.util.ArrayList;
import java.util.List;

public class DeliveryRepoImpl implements DeliveryRepo {
    private final List<Delivery> deliveries = new ArrayList<>();
    @Override
    public Delivery saveDelivery(Delivery delivery) {
        deliveries.add(delivery);
        return delivery;
    }

    @Override
    public Delivery updateDelivery(Delivery delivery) {
        int index = deliveries.indexOf(delivery);
        if (index != -1) deliveries.set(index, delivery);
        return delivery;
    }

    @Override
    public Delivery getDeliveryById(Long id) {
        return deliveries.stream()
                         .filter(delivery -> delivery.getId().equals(id))
                         .findFirst()
                         .orElse(null);
    }
}
