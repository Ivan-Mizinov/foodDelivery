package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.DeliveryEntity;
import org.springframework.data.repository.CrudRepository;

public interface DeliveryRepository extends CrudRepository<DeliveryEntity, Long> {
}
