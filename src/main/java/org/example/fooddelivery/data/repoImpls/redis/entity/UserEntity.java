package org.example.fooddelivery.data.repoImpls.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RedisHash("users")
public class UserEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private String name;
    @Indexed
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;
}
