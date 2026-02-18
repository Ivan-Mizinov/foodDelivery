package org.example.fooddelivery.data.repoImpls.redis.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.redis.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.redis.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.data.repoImpls.redis.DeliveryRedisRepository;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapter_Redis")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryRedisRepository deliveryRepository;
    private final OrderRepoAdapter orderRepoAdapter;
    private final DeliveryMapper mapper;

    public DeliveryRepoAdapter(DeliveryRedisRepository deliveryRepository,
                               @Qualifier("OrderRepoAdapter_Redis") OrderRepoAdapter orderRepoAdapter,
                               @Qualifier("DeliveryMapper_Redis") DeliveryMapper deliveryMapper) {
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
