package org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.DeliveryEntity;
import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.model.IDelivery;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("DeliveryMapper_Neo4j")
public class DeliveryMapper {
    private final OrderMapper orderMapper;

    public DeliveryMapper(@Qualifier("OrderMapper_Neo4j") OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
        if (iDelivery == null) return null;
        return new DeliveryEntity(
                UUIDUtils.getUUIDFromLong(iDelivery.getId()),
                iDelivery.getAddress(),
                iDelivery.getPhone(),
                iDelivery.getDeliveryTime(),
                orderMapper.getOrderEntityFromIOrder(iDelivery.getOrder())
        );
    }

    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity) {
        if (deliveryEntity == null) return null;

        return Delivery.builder()
                .id(UUIDUtils.getLongFromUUID(deliveryEntity.getId()))
                .phone(deliveryEntity.getPhone())
                .address(deliveryEntity.getAddress())
                .deliveryTime(deliveryEntity.getDeliveryTime())
                .order(orderMapper.getIOrderFromOrderEntity(deliveryEntity.getOrder()))
                .build();
    }
}
