package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fooddelivery.domain.model.IUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data @Builder
@AllArgsConstructor @NoArgsConstructor
@Table("users")
public class UserEntity implements IUser {
    @Id
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;
}
