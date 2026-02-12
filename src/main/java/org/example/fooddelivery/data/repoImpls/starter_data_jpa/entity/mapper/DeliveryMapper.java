package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.model.IOrder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

//@Component
public class DeliveryMapper {

    private final ModelMapper modelMapper;

    public DeliveryMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
        if (iDelivery == null) return null;
        return modelMapper.map(iDelivery, DeliveryEntity.class);
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iOrder) {
        if (deliveryEntity == null) return null;
        return modelMapper.map(deliveryEntity, Delivery.class);
    }
}
