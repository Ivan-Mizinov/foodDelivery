package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.DeliveryMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.DeliveryPSRepository;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component("DeliveryRepoAdapterPS")
public class DeliveryRepoAdapter implements DeliveryRepo {
    private final DeliveryPSRepository deliveryRepository;
    private final DeliveryMapper deliveryMapper;
    private final OrderRepoAdapter orderRepoAdapter;

    public DeliveryRepoAdapter(DeliveryPSRepository deliveryRepository,
                               DeliveryMapper deliveryMapper,
                               @Qualifier("OrderRepoAdapterPS") OrderRepoAdapter orderRepoAdapter) {
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

    public Page<IDelivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable)
                .map(this::convertToIDelivery);
    }

    public List<IDelivery> getAllDeliveries(Sort sort) {
        return deliveryRepository.findAll(sort)
                .stream()
                .map(this::convertToIDelivery)
                .toList();
    }

    public Page<IDelivery> getDeliveriesByAddressContaining(String address, Pageable pageable) {
        return deliveryRepository.findByAddressContaining(address, pageable)
                .map(this::convertToIDelivery);
    }

    public Page<IDelivery> getDeliveriesByPhoneContaining(String phone, Pageable pageable) {
        return deliveryRepository.findByPhoneContaining(phone,pageable)
                .map(this::convertToIDelivery);
    }

    public List<IDelivery> getDeliveriesByDeliveryTimeBetweenOrderByDeliveryTimeDesc(LocalDateTime from, LocalDateTime to, Sort sort) {
        return deliveryRepository.findByDeliveryTimeBetweenOrderByDeliveryTimeDesc(from, to, sort)
                .stream()
                .map(this::convertToIDelivery)
                .toList();
    }

    public Page<IDelivery> getDeliveriesByOrderId(Long orderId, Pageable pageable) {
        return deliveryRepository.findByOrderId(orderId,pageable)
                .map(this::convertToIDelivery);
    }

    private IDelivery convertToIDelivery(DeliveryEntity deliveryEntity) {
        return deliveryMapper.getIDeliveryFromDeliveryEntity(
                deliveryEntity,
                orderRepoAdapter.getOrderById(deliveryEntity.getOrderId())
        );
    }
}
