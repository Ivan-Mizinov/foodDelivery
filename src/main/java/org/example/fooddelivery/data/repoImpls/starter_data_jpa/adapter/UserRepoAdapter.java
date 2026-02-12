package org.example.fooddelivery.data.repoImpls.starter_data_jpa.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity.mapper.UserMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jpa.UserJpaRepository;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("URwJPA")
public class UserRepoAdapter implements UserRepo {

    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserJpaRepository userRepository,
                          @Qualifier("UserMapper_JPA") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser user) {
        return userRepository.save(
                userMapper.getUserEntityFromIUser(user)
        );
    }

    @Override
    public IUser updateUser(IUser user) {
        return saveUser(user);
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(userMapper.getUserEntityFromIUser(user));
    }

    @Override
    public IUser getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::getIUserFromUserEntity).orElse(null);
    }
}
