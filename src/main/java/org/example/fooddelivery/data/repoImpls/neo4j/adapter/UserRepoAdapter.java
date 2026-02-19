package org.example.fooddelivery.data.repoImpls.neo4j.adapter;

import org.example.fooddelivery.data.repoImpls.neo4j.UserNeo4jRepository;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.neo4j.entity.mapper.UserMapper;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component("UserRepoAdapter_Neo4j")
public class UserRepoAdapter implements UserRepo {

    private final UserNeo4jRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserNeo4jRepository userRepository,
                           @Qualifier("UserMapper_Neo4j") UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public IUser saveUser(IUser user) {
        return userMapper.getIUserFromUserEntity(
                userRepository.save(userMapper.getUserEntityFromIUser(user)));
    }

    @Override
    public IUser updateUser(IUser user) {
        return saveUser(user);
    }

    @Override
    public IUser getUserByEmail(String email) {
        return userMapper.getIUserFromUserEntity(userRepository.findByEmail(email));
    }

    @Override
    public void deleteUser(IUser user) {
        userRepository.delete(userMapper.getUserEntityFromIUser(user));
    }

    public IUser getUserById(UUID id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        return userEntity.map(userMapper::getIUserFromUserEntity).orElse(null);
    }
}
