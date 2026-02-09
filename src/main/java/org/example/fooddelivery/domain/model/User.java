package org.example.fooddelivery.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class User implements IUser {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;
}
