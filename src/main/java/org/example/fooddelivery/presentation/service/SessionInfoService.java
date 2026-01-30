package org.example.fooddelivery.presentation.service;

import lombok.Getter;
import lombok.Setter;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.presentation.service.dto.OrderDto;
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
    private String password;
    private String address;
    private String email;
    private String telegram;
    private List<IMenuItem> cart;

    public void setUserInfo(IUser user) {
        setUsername(user.getName());
        setPhone(user.getPhone());
        setAddress(user.getAddress());
        setEmail(user.getEmail());
        setTelegram(user.getTelegram());
        setPassword(user.getPassword());
    }

    public BigDecimal getTotalPrice() {
        return cart.stream().map(IMenuItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public IUser getUser() {
        return User.builder()
                .name(username)
                .email(email)
                .phone(phone)
                .telegram(telegram)
                .address(address)
                .build();
    }

    public void setInfoFromOrderDto(OrderDto orderDto) {
        setUsername(orderDto.getUsername());
        setPhone(orderDto.getPhone());
        setAddress(orderDto.getAddress());
    }
}
