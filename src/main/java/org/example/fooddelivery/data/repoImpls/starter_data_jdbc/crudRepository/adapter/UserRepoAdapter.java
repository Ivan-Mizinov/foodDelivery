package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("UserRepoAdapterCrud")
public class UserRepoAdapter implements UserRepo {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(@Qualifier("UserRepoExtCrudRepo") UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser iUser) {
        return userMapper.getIUserFromUserEntity(
                userRepository.save(
                        userMapper.getUserEntityFromIUser(iUser))
        );
    }

    @Override
    public IUser updateUser(IUser iUser) {
        return saveUser(iUser);
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void deleteUser(IUser iUser) {
        userRepository.delete(
                userMapper.getUserEntityFromIUser(iUser));
    }

    @Override
    public IUser getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::getIUserFromUserEntity)
                .orElse(null);
    }
}
