package org.example.fooddelivery.data.repoImpls.starter_data_jpa.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.OrderJpaRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.OrderEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.DeliveryJpaRepository;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("DeliveryRepoAdapter_JPA")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryJpaRepository deliveryRepository;
    private final OrderJpaRepository orderRepository;
    private final DeliveryMapper mapper;

    public DeliveryRepoAdapter(DeliveryJpaRepository deliveryRepository, OrderJpaRepository orderRepository,
                               @Qualifier("DeliveryMapper_JPA") DeliveryMapper deliveryMapper) {
        this.deliveryRepository = deliveryRepository;
        this.orderRepository = orderRepository;
        this.mapper = deliveryMapper;
    }

    @Transactional
    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        DeliveryEntity deliveryEntity = mapper.getDeliveryEntityFromIDelivery(delivery);

        if (deliveryEntity.getOrder() != null) {
            OrderEntity orderEntity = orderRepository.findById(delivery.getOrder().getId())
                    .orElseThrow(() -> new RuntimeException("Order not found with id " + delivery.getOrder().getId()));
            deliveryEntity.setOrder(orderEntity);
        }

        return mapper.getIDeliveryFromDeliveryEntity(deliveryRepository.save(deliveryEntity));
    }

    @Transactional
    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        return saveDelivery(delivery);
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id " + id));
        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity);
    }
}
