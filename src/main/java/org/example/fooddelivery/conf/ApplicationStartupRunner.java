package org.example.fooddelivery.conf;

import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.presentation.controller.UserController;
import org.springframework.boot.CommandLineRunner;

public class ApplicationStartupRunner implements CommandLineRunner {
    private final UserController userController;

    public ApplicationStartupRunner(UserController userController) {
        this.userController = userController;
    }

    @Override
    public void run(String... args) throws Exception {
        userController.createUser(new User());
        userController.deleteUser(new User());
    }
}
