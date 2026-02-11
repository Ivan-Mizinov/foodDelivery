package org.example.fooddelivery.presentation.service;

import org.example.fooddelivery.domain.interractor.DeliveryInterractor;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class DeliveryService extends DeliveryInterractor {
    public DeliveryService(@Qualifier("DeliveryRepoAdapterPS") DeliveryRepo repo) {
        super(repo);
    }
}
