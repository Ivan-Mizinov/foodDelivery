package org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper;

import org.example.fooddelivery.data.repoImpls.cassandra.UUIDUtils;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.UserEntity;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.springframework.stereotype.Component;

@Component("UserMapper_Neo4j")
public class UserMapper {
    public UserEntity getUserEntityFromIUser(IUser iUser) {
        if (iUser == null) return null;
        UserEntity userEntity = new UserEntity();
        userEntity.setId(UUIDUtils.getUUIDFromLong(iUser.getId()));
        userEntity.setName(iUser.getName());
        userEntity.setAddress(iUser.getAddress());
        userEntity.setPassword(iUser.getPassword());
        userEntity.setEmail(iUser.getEmail());
        userEntity.setPhone(iUser.getPhone());
        userEntity.setTelegram(iUser.getTelegram());
        return userEntity;
    }

    public IUser getIUserFromUserEntity(UserEntity userEntity) {
        if (userEntity == null) return null;
        User user =new User();
        user.setId(UUIDUtils.getLongFromUUID(userEntity.getId()));
        user.setName(userEntity.getName());
        user.setAddress(userEntity.getAddress());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setPhone(userEntity.getPhone());
        user.setTelegram(userEntity.getTelegram());
        return user;
    }
}
