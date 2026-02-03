package org.example.fooddelivery.domain.repo;

import org.example.fooddelivery.domain.model.IUser;

public interface UserRepo {
    IUser saveUser(IUser user);
    IUser updateUser(IUser user);
    IUser getUserByEmail(String email);
    void deleteUser(IUser user);
    IUser getUserById(Long id);
}
