package org.example.fooddelivery.data.repoImpls.cassandra.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.DeliveryCassandraRepository;
import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapter_Cass")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryCassandraRepository deliveryRepository;
    private final OrderRepoAdapter orderRepoAdapter;
    private final DeliveryMapper mapper;

    public DeliveryRepoAdapter(DeliveryCassandraRepository deliveryRepository,
                               @Qualifier("OrderRepoAdapter_Cass") OrderRepoAdapter orderRepoAdapter,
                               @Qualifier("DeliveryMapper_Cass") DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepoAdapter = orderRepoAdapter;
        this.mapper = deliveryMapper;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity savedDelivery = mapper.getDeliveryEntityFromIDelivery(delivery);
        DeliveryEntity deliveryEntity = deliveryRepository.save(savedDelivery);
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity, delivery.getOrder());
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return saveDelivery(delivery);
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(UUIDUtils.getUUIDFromLong(id))
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id " + id));
        return mapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId()));
    }
}
