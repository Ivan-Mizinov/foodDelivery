package org.example.fooddelivery.presentation.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.conf.AuthUtils;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.presentation.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final AuthUtils authUtils;

    public UserController(UserService service, AuthUtils authUtils) {
        this.service = service;
        this.authUtils = authUtils;
    }

    @GetMapping("/register")
    public String newUser(
            Model model
    ) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute User user,
            Model model
    ) {
        String encodedPassword = authUtils.encodePassword(user.getPassword());
        user.setPassword(encodedPassword);
        service.createUser(user);
        log.info(user.toString());
        model.addAttribute("msg", "User registered successfully");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(
            Model model
    ) {
        model.addAttribute("email", "");
        model.addAttribute("password", "");
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            Model model
    ) {
        try {
            User user = service.getUserByEmail(email);
            if (authUtils.authenticate(password, user.getPassword())) {
                return "redirect:/menu";
            }
            model.addAttribute("error", "Invalid email or password");
            return "login";
        } catch (Exception e) {
            model.addAttribute("error", "Login failed:" + e.getMessage());
            return "login";
        }
    }

    @PostMapping("/delete")
    public String deleteUser(
            @RequestParam String email
    ) {
        User user = service.getUserByEmail(email);
        service.deleteUser(user);
        return "redirect:/users/register";
    }
}
