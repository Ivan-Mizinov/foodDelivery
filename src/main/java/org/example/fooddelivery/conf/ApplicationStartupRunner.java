package org.example.fooddelivery.conf;

import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.presentation.controller.UserController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    private final UserController userController;

    public ApplicationStartupRunner(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void run(String... args) {
//        User user = new User();
//        userController.createUser(user);
//        userController.deleteUser(user);
    }
}
