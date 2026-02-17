package org.example.fooddelivery.data.repoImpls.cassandra.entity;

import lombok.*;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity {
    @PrimaryKey
    private UUID id = UUID.randomUUID();
    private String name;
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;

    @Column("order_id")
    private List<UUID> orders;
}
