package org.example.fooddelivery.presentation.service;

import lombok.Getter;
import lombok.Setter;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IUser;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.math.BigDecimal;
import java.util.List;

@Service
@SessionScope
@Getter
@Setter
public class SessionInfoService {
    private String username;
    private String phone;
    private String address;
    private String email;
    private List<IMenuItem> cart;

    public void setUserInfo(IUser user) {
        setUsername(user.getName());
        setPhone(user.getPhone());
        setAddress(user.getAddress());
        setEmail(user.getEmail());
    }

    public BigDecimal getTotalPrice() {
        return cart.stream().map(IMenuItem::getPrice)
                   .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
