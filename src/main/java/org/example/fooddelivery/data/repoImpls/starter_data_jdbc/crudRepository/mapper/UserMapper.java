package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.UserEntity;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEntity getUserEntityFromIUser(IUser iUser) {
        return UserEntity.builder()
                .id(iUser.getId())
                .name(iUser.getName())
                .email(iUser.getEmail())
                .password(iUser.getPassword())
                .telegram(iUser.getTelegram())
                .phone(iUser.getPhone())
                .address(iUser.getAddress())
                .build();
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .telegram(userEntity.getTelegram())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .build();
    }
}
