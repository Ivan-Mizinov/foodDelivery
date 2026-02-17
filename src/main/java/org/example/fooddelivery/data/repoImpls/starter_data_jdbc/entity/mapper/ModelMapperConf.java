package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.entity.MenuItemEntity;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.DeliveryEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.OrderEntity;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.model.IOrder;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.model.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConf {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT)
                .setSkipNullEnabled(true);

        //        for starter_data_jdbc
        modelMapper.typeMap(IOrder.class, OrderEntity.class)
                .addMappings(mapper ->
                        mapper.skip(OrderEntity::setUserId));

        modelMapper.typeMap(OrderEntity.class, IOrder.class)
                .addMappings(mapper -> {
                    mapper.skip(IOrder::setUser);
                    mapper.skip(IOrder::setItemList);
                });

        modelMapper.typeMap(IDelivery.class, DeliveryEntity.class)
                .addMappings(mapper ->
                        mapper.skip(DeliveryEntity::setOrderId));

        modelMapper.typeMap(DeliveryEntity.class, IDelivery.class)
                .addMappings(mapper ->
                        mapper.skip(IDelivery::setOrder));

        // for cassandra
        modelMapper.typeMap(UserEntity.class, User.class)
                .addMappings(mapper ->
                        mapper.skip(User::setId));
        modelMapper.typeMap(User.class, UserEntity.class)
                .addMappings(mapper ->
                        mapper.skip(UserEntity::setId));

        modelMapper.typeMap(MenuItemEntity.class, MenuItem.class)
                .addMappings(mapper ->
                        mapper.skip(MenuItem::setId));
        modelMapper.typeMap(MenuItem.class, MenuItemEntity.class)
                .addMappings(mapper ->
                        mapper.skip(MenuItemEntity::setId));
        return modelMapper;
    }

}
