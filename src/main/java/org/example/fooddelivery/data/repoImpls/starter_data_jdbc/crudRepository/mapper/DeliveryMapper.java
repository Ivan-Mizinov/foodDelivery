package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.DeliveryEntity;
import org.example.fooddelivery.domain.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
        if (iDelivery == null) return null;
        DeliveryEntity deliveryEntity = modelMapper.map(iDelivery, DeliveryEntity.class);
        deliveryEntity.setOrderId(iDelivery.getOrder().getId());
        return deliveryEntity;
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iOrder) {
        if (deliveryEntity == null) return null;
        IDelivery delivery = modelMapper.map(deliveryEntity, Delivery.class);
        delivery.setOrder(iOrder);
        return delivery;
    }
}
