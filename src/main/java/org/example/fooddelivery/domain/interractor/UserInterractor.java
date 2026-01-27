package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;

public class UserInterractor {
    private final UserRepo userRepo;

    public UserInterractor(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User saveUser(User user) {
        return userRepo.saveUser(user);
    }

    public void deleteUser(User user) {
        userRepo.deleteUser(user);
    }

    public User getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public User updateUser(User user) {
        return userRepo.updateUser(user);
    }
}
