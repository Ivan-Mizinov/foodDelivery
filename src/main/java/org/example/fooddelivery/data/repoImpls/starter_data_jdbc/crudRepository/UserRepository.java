package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("UserRepoExtCrudRepo")
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    void deleteUserByEmail(String email);
    UserEntity findByEmail(String email);
}
