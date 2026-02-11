package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.DeliveryRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryRepoAdapterCrud")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderRepoAdapter orderRepoAdapter;

    public DeliveryRepoAdapter(@Qualifier("DeliveryRepoExtCrudRepo") DeliveryRepository deliveryRepository,
                               DeliveryMapper deliveryMapper,
                               @Qualifier("OrderRepoAdapterCrud") OrderRepoAdapter orderRepoAdapter) {
        this.deliveryRepository = deliveryRepository;
        this.deliveryMapper = deliveryMapper;
        this.orderRepoAdapter = orderRepoAdapter;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity deliveryEntity = deliveryRepository.save(deliveryMapper.getDeliveryEntityFromIDelivery(delivery));
        return deliveryMapper.getIDeliveryFromDeliveryEntity(deliveryEntity, delivery.getOrder());
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return saveDelivery(delivery);
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id " + id));
        return deliveryMapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId()));
    }
}
