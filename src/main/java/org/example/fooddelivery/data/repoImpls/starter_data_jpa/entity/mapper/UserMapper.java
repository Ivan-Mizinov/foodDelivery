package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.example.fooddelivery.domain.model.IUser;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("UserMapper_JPA")
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity getUserEntityFromIUser(IUser iUser) {
        if (iUser == null) return null;
        return modelMapper.map(iUser, UserEntity.class);
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity) {
        if (userEntity == null) return null;
        return modelMapper.map(userEntity, IUser.class);
    }
}
