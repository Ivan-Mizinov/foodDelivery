package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.User;

public interface UserRepo {
    User saveUser(User user);
    User updateUser(User user);
    User getUserByEmail(String email);
    void deleteUser(User user);
}
