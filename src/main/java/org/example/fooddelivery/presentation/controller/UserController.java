package org.example.fooddelivery.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.conf.AuthUtils;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.presentation.service.SessionInfoService;
import org.example.fooddelivery.presentation.service.UserService;
import org.example.fooddelivery.presentation.service.dto.LoginCredential;
import org.example.fooddelivery.presentation.service.dto.UserDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final AuthUtils authUtils;
    private final SessionInfoService sessionInfoService;

    @GetMapping("/register")
    public String newUser(
            Model model
    ) {
        model.addAttribute("userDto", new UserDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @Valid @ModelAttribute("userDto") UserDto userDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("sessionInfoService", sessionInfoService);
            return "register";
        }

        String encodedPassword = authUtils.encodePassword(userDto.getPassword());
        userDto.setPassword(encodedPassword);
        userService.createUser(userDto);
        sessionInfoService.setUserInfo(userDto);

        model.addAttribute("msg", "User registered successfully");
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String showLoginForm(
            Model model
    ) {
        model.addAttribute("credential", new LoginCredential());
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(
            @Valid @ModelAttribute(name = "credential") LoginCredential credential,
            BindingResult result,
            Model model
    ) {
        try {
            if (result.hasErrors()) {
                model.addAttribute("credential",  credential);
                return "login";
            }
            IUser user = userService.getUserByEmail(credential.getEmail());
            if (authUtils.authenticate(credential.getPassword(), user.getPassword())) {
                sessionInfoService.setUserInfo(user);
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
        IUser user = userService.getUserByEmail(email);
        userService.deleteUser(user);
        return "redirect:/users/register";
    }
}
