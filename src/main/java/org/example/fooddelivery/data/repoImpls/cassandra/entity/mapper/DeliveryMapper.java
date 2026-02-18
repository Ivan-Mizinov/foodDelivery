//package org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper;
//
//import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
//import org.example.fooddelivery.data.repoImpls.cassandra.entity.DeliveryEntity;
//import org.example.fooddelivery.domain.model.Delivery;
//import org.example.fooddelivery.domain.model.IDelivery;
//import org.example.fooddelivery.domain.model.IOrder;
//import org.springframework.stereotype.Component;
//
//@Component("DeliveryMapper_Cass")
//public class DeliveryMapper {
//
//    public DeliveryEntity getDeliveryEntityFromIDelivery(IDelivery iDelivery) {
//        if (iDelivery == null) return null;
//        return new DeliveryEntity(
//                UUIDUtils.getUUIDFromLong(iDelivery.getId()),
//                iDelivery.getAddress(),
//                iDelivery.getPhone(),
//                iDelivery.getDeliveryTime(),
//                UUIDUtils.getUUIDFromLong(iDelivery.getOrder().getId())
//        );
//    }
//
//    public IDelivery getIDeliveryFromDeliveryEntity(DeliveryEntity deliveryEntity, IOrder iOrder) {
//        if (deliveryEntity == null) return null;
//
//        return Delivery.builder()
//                .id(UUIDUtils.getLongFromUUID(deliveryEntity.getId()))
//                .phone(deliveryEntity.getPhone())
//                .address(deliveryEntity.getAddress())
//                .deliveryTime(deliveryEntity.getDeliveryTime())
//                .order(iOrder)
//                .build();
//    }
//}
