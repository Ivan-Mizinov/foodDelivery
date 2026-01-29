package org.example.fooddelivery.domain.model;

import jakarta.annotation.PostConstruct;
import lombok.Data;

@Data
public class User implements IUser {
    private Long id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String telegram;
    private String address;

    @PostConstruct
    public void init() {
        System.out.println("init method is called");
        this.setTelegram("@telegram" + this.id);
    }
}
