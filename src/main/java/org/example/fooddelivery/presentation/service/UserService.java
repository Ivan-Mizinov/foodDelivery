package org.example.fooddelivery.presentation.service;

import org.example.fooddelivery.domain.interractor.UserInterractor;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserInterractor {

    public UserService(UserRepo userRepo) {
        super(userRepo);
    }
}
