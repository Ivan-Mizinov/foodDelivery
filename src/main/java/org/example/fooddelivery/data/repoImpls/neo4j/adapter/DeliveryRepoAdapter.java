package org.example.fooddelivery.data.repoImpls.neo4j.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.neo4j.DeliveryNeo4jRepository;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapter_Neo4j")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryNeo4jRepository deliveryRepository;
    private final DeliveryMapper mapper;

    public DeliveryRepoAdapter(DeliveryNeo4jRepository deliveryRepository,
                               @Qualifier("DeliveryMapper_Neo4j") DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.mapper = deliveryMapper;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryRepository.save(mapper.getDeliveryEntityFromIDelivery(delivery))
        );
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return saveDelivery(delivery);
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(UUIDUtils.getUUIDFromLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id " + id));
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity);
    }
}
