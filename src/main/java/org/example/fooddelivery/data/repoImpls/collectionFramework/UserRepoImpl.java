package org.example.fooddelivery.data.repoImpls.collectionFramework;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("URwAL")
@Slf4j
public class UserRepoImpl implements UserRepo {
    private final List<User> users = new ArrayList<>();

    @Override
    public User saveUser(User user) {
        users.add(user);
        log.info("User created with ArrayList");
        return user;
    }

    @Override
    public void deleteUser(User user) {
        log.info("User deleted with ArrayList");
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
