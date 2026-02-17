package org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.UserEntity;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component("UserMapper_Cass")
public class UserMapper {

    private final ModelMapper modelMapper;

    public UserMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserEntity getUserEntityFromIUser(IUser iUser) {
        if (iUser == null) return null;
        UserEntity userEntity = modelMapper.map(iUser, UserEntity.class);
        userEntity.setId(UUIDUtils.getUUIDFromLong(iUser.getId()));
        return userEntity;
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity) {
        if (userEntity == null) return null;
        User user = modelMapper.map(userEntity, User.class);
        user.setId(UUIDUtils.getLongFromUUID(userEntity.getId()));
        return user;
    }
}
