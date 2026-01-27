package org.example.fooddelivery.data.repoImpls;

import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;

import java.util.ArrayList;
import java.util.List;

public class UserRepoImpl implements UserRepo {
    private final List<User> users = new ArrayList<>();

    @Override
    public User saveUser(User user) {
        users.add(user);
        return user;
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
    }

    @Override
    public User getUserByEmail(String email) {
        return users.stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public User updateUser(User user) {
        int index = users.indexOf(user);
        if (index != -1) users.set(index, user);
        return user;

    }
}
