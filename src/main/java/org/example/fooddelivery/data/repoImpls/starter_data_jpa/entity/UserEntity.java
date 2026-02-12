package org.example.fooddelivery.data.repoImpls.starter_data_jpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.fooddelivery.domain.model.IUser;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity implements IUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String phone;
    private String telegram;
    private String address;

    @OneToMany(mappedBy = "user")
    private List<OrderEntity> orders;
}
