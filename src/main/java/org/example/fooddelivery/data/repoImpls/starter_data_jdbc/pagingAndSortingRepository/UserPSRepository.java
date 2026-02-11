package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.pagingAndSortingRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.UserRepository;
import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserPSRepository extends PagingAndSortingRepository<UserEntity, Long>, UserRepository {

    @Override
    Page<UserEntity> findAll(Pageable pageable);

    @Override
    Iterable<UserEntity> findAll(Sort sort);

    Page<UserEntity> findByEmailContaining(String email, Pageable pageable);
    List<UserEntity> findAllByOrderByNameAsc();
    Page<UserEntity> findAllByOrderByNameAsc(Pageable pageable);
}
