package org.example.fooddelivery.domain.interractor;

import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;

public class UserInterractor {
    protected final UserRepo userRepo;

    public UserInterractor(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public IUser createUser(IUser user) {
        return userRepo.saveUser(user);
    }

    public void deleteUser(IUser user) {
        userRepo.deleteUser(user);
    }

    public IUser getUserByEmail(String email) {
        return userRepo.getUserByEmail(email);
    }

    public IUser updateUser(IUser user) {
        return userRepo.updateUser(user);
    }
}
