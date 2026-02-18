package org.example.fooddelivery.data.repoImpls.redis.adapter;

import org.example.fooddelivery.data.repoImpls.redis.UserRedisRepository;
import org.example.fooddelivery.data.repoImpls.redis.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.redis.entity.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component("UserRepoAdapter_Redis")
public class UserRepoAdapter implements UserRepo {
    private final UserRedisRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserRedisRepository userRepository,
                           @Qualifier("UserMapper_Redis") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser user) {
        UserEntity userEntity = userMapper.getUserEntityFromIUser(user);
        return userMapper.getIUserFromUserEntity(userRepository.save(userEntity));
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
        deleteUserByEmail(user.getEmail());
    }

    private void deleteUserByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) userRepository.delete(userEntity);
    }

    public IUser getUserById(UUID id) {
        return userMapper.getIUserFromUserEntity(userRepository.findById(id).orElse(null));
    }
}
