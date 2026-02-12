package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.DeliveryEntity;
import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.model.IDelivery;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryMapper_JPA")
public class DeliveryMapper {

    private final ModelMapper modelMapper;
    private final OrderMapper orderMapper;

    public DeliveryMapper(ModelMapper modelMapper,
                          @Qualifier("OrderMapper_JPA") OrderMapper orderMapper) {
        this.modelMapper = modelMapper;
        this.orderMapper = orderMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
        if (iDelivery == null) return null;
        return modelMapper.map(iDelivery, DeliveryEntity.class);
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity) {
        if (deliveryEntity == null) return null;
        return Delivery.builder()
                .id(deliveryEntity.getId())
                .phone(deliveryEntity.getPhone())
                .address(deliveryEntity.getAddress())
                .deliveryTime(deliveryEntity.getDeliveryTime())
                .order(orderMapper.getIOrderFromOrderEntity(deliveryEntity.getOrder()))
                .build();
    }
}
