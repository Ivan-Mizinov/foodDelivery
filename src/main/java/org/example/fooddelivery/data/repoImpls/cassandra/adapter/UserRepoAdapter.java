//package org.example.fooddelivery.data.repoImpls.cassandra.adapter;
//
//import org.example.fooddelivery.data.repoImpls.cassandra.UserCassandraRepository;
//import org.example.fooddelivery.data.repoImpls.cassandra.entity.UserEntity;
//import org.example.fooddelivery.data.repoImpls.cassandra.entity.mapper.UserMapper;
//import org.example.fooddelivery.domain.model.IUser;
//import org.example.fooddelivery.domain.repo.UserRepo;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Component("UserRepoAdapter_Cass")
//public class UserRepoAdapter implements UserRepo {
//    private final UserCassandraRepository userRepository;
//    private final UserMapper userMapper;
//
//    public UserRepoAdapter(UserCassandraRepository userRepository,
//                           @Qualifier("UserMapper_Cass") UserMapper userMapper) {
//        this.userRepository = userRepository;
//        this.userMapper = userMapper;
//    }
//
//    @Override
//    public IUser saveUser(IUser user) {
//        UserEntity userEntity = userMapper.getUserEntityFromIUser(user);
//        return userMapper.getIUserFromUserEntity(userRepository.save(userEntity));
//    }
//
//    @Override
//    public IUser updateUser(IUser user) {
//        return userMapper.getIUserFromUserEntity(userRepository.save(userMapper.getUserEntityFromIUser(user)));
//    }
//
//    @Override
//    public IUser getUserByEmail(String email) {
//        return userMapper.getIUserFromUserEntity(userRepository.findByEmail(email));
//    }
//
//    @Override
//    public void deleteUser(IUser user) {
//        deleteUserByEmail(user.getEmail());
//    }
//
//    private void deleteUserByEmail(String email) {
//        UserEntity userEntity = userRepository.findByEmail(email);
//        if (userEntity != null) userRepository.delete(userEntity);
//    }
//
//    public IUser getUserById(UUID id) {
//        return userMapper.getIUserFromUserEntity(userRepository.findById(id).orElse(null));
//    }
//}
