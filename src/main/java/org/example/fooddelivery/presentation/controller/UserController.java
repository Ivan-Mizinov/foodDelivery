package org.example.fooddelivery.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.presentation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/new_user")
    public String newUser(
            @RequestParam String name,
            Model model
    ) {
        model.addAttribute("name", name);
        return "new_user";
    }
}
