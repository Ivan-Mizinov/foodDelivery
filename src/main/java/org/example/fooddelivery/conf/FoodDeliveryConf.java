package org.example.fooddelivery.conf;

import org.example.fooddelivery.data.repoImpls.UserRepoImpl;
import org.example.fooddelivery.data.repoImpls.UserRepoImplWithLinkedList;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.example.fooddelivery.presentation.controller.UserController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class FoodDeliveryConf {

    @Bean
    public UserRepo userRepo() {
        return new UserRepoImpl();
    }

    @Bean
    @Primary
    public UserRepo userRepoLinkedList() {
        return new UserRepoImplWithLinkedList();
    }

    @Bean
    public CommandLineRunner commandlineRunner(UserController userController) {
        return new ApplicationStartupRunner(userController);
    }
}
