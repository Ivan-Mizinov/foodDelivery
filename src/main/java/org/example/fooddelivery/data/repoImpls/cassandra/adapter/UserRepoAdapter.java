package org.example.fooddelivery.data.repoImpls.cassandra.adapter;

import org.example.fooddelivery.data.repoImpls.cassandra.UserCassandraRepository;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("UserRepoAdapter_Cass")
public class UserRepoAdapter implements UserRepo {
    private final UserCassandraRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserCassandraRepository userRepository,
                           @Qualifier("UserMapper_Cass") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser user) {
        UserEntity userEntity = userMapper.getUserEntityFromIUser(user);
        if (userEntity.getId() == null) userEntity.setId(UUID.randomUUID());
        return userMapper.getIUserFromUserEntity(userRepository.save(userMapper.getUserEntityFromIUser(user)));
    }

    @Override
    public IUser updateUser(IUser user) {
        return userMapper.getIUserFromUserEntity(userRepository.save(userMapper.getUserEntityFromIUser(user)));
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userMapper.getIUserFromUserEntity(userRepository.findByEmail(email));
    }

    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(userMapper.getUserEntityFromIUser(user));
    }

    @Override
    public IUser getUserById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        try {
            UUID uuid = UUID.fromString(String.valueOf(id));
            UserEntity userEntity = userRepository.findById(uuid).orElse(null);
            return userEntity != null
                    ? userMapper.getIUserFromUserEntity(userEntity)
                    : null;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("id is not a valid UUID");
        }
    }
}
