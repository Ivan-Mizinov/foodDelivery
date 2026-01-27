package org.example.fooddelivery.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.presentation.service.UserService;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    public User createUser(User user) {
        return userService.saveUser(user);
    }

    public void deleteUser(User user) {
        userService.deleteUser(user);
    }
}
