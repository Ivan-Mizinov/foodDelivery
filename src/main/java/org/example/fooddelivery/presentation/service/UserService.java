package org.example.fooddelivery.presentation.service;

import org.example.fooddelivery.domain.interractor.UserInterractor;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class UserService extends UserInterractor {

    public UserService(@Qualifier("UserRepoAdapterCrud") UserRepo userRepo) {
        super(userRepo);
    }
}
