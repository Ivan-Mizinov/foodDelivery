package org.example.fooddelivery.domain.interractor;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;

@RequiredArgsConstructor
public class UserInterractor {
    private final UserRepo userRepo;

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
