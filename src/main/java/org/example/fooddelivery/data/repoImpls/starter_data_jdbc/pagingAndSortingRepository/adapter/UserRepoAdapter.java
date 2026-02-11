package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.adapter;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.mapper.UserMapper;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository.UserPSRepository;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("UserRepoAdapterPS")
public class UserRepoAdapter implements UserRepo {
    private final UserPSRepository userRepository;
    private final UserMapper userMapper;

    public UserRepoAdapter(UserPSRepository userRepository, UserMapper userMapper) {
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

    public Page<IUser> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::getIUserFromUserEntity);
    }

    public List<IUser> getAllUsers(Sort sort) {
        return ((List<UserEntity>) userRepository.findAll(sort))
                .stream().map(userMapper::getIUserFromUserEntity).collect(Collectors.toList());
    }

    public Page<IUser> findByEmailContaining(String email, Pageable pageable) {
        return userRepository.findByEmailContaining(email, pageable)
                .map(userMapper::getIUserFromUserEntity);
    }

    public List<IUser> findAllByOrderByNameAsc() {
        return userRepository.findAllByOrderByNameAsc().stream()
                .map(userMapper::getIUserFromUserEntity).toList();
    }

    public Page<IUser> findAllByOrderByNameDesc(Pageable pageable) {
        return userRepository.findAllByOrderByNameAsc(pageable)
                .map(userMapper::getIUserFromUserEntity);
    }
}
