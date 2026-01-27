package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.User;

public interface UserRepository {
    User saveUser(User user);
    void deleteUser(User user);
    User getUserByEmail(String email);
    User updateUser(User user);
}
