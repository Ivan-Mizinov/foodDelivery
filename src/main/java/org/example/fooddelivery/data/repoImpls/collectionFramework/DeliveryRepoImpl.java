package org.example.fooddelivery.data.repoImpls.collectionFramework;

import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class DeliveryRepoImpl implements DeliveryRepo {
    private final List<IDelivery> deliveries = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);
    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        delivery.setId(nextId.getAndIncrement());
        deliveries.add(delivery);
        return delivery;
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        int index = deliveries.indexOf(delivery);
        if (index != -1) deliveries.set(index, delivery);
        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        return deliveries.stream()
                         .filter(delivery -> delivery.getId().equals(id))
                         .findFirst()
                         .orElse(null);
    }
}
