//package org.example.fooddelivery.data.repoImpls.mongodb.adapter;
//
//import org.example.fooddelivery.data.repoImpls.mongodb.DeliveryMongoRepository;
//import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
//import org.example.fooddelivery.data.repoImpls.mongodb.entity.DeliveryEntity;
//import org.example.fooddelivery.data.repoImpls.mongodb.entity.mapper.DeliveryMapper;
//import org.example.fooddelivery.domain.model.IDelivery;
//import org.example.fooddelivery.domain.repo.DeliveryRepo;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//@Component("DeliveryRepoAdapter_MongoDB")
//public class DeliveryRepoAdapter implements DeliveryRepo {
//    private final DeliveryMongoRepository deliveryRepository;
//    private final OrderRepoAdapter orderRepoAdapter;
//    private final DeliveryMapper mapper;
//
//    public DeliveryRepoAdapter(DeliveryMongoRepository deliveryRepository,
//                               @Qualifier("OrderRepoAdapter_MongoDB") OrderRepoAdapter orderRepoAdapter,
//                               @Qualifier("DeliveryMapper_MongoDB") DeliveryMapper deliveryMapper) {
//        this.deliveryRepository = deliveryRepository;
//        this.orderRepoAdapter = orderRepoAdapter;
//        this.mapper = deliveryMapper;
//    }
//
//    @Override
//    public IDelivery saveDelivery(IDelivery delivery) {
//        DeliveryEntity savedDelivery = mapper.getDeliveryEntityFromIDelivery(delivery);
//        DeliveryEntity deliveryEntity = deliveryRepository.save(savedDelivery);
//        return mapper.getIDeliveryFromDeliveryEntity(deliveryEntity, delivery.getOrder());
//    }
//
//    @Override
//    public IDelivery updateDelivery(IDelivery delivery) {
//        return saveDelivery(delivery);
//    }
//
//    @Override
//    public IDelivery getDeliveryById(Long id) {
//        DeliveryEntity deliveryEntity = deliveryRepository.findById(UUIDUtils.getUUIDFromLong(id))
//                .orElseThrow(() -> new IllegalArgumentException("Delivery not found with id " + id));
//        return mapper.getIDeliveryFromDeliveryEntity(
//                deliveryEntity,
//                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId()));
//    }
//}
