package org.example.fooddelivery.data.repoImpls.collectionFramework;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository("URwLL")
@Slf4j
public class UserRepoImplWithLinkedList implements UserRepo {
    private final LinkedList<User> users = new LinkedList<>();
    @Override
    public User saveUser(User user) {
        users.add(user);
        log.info("User created with LinkedList");
        return user;
    }

    @Override
    public void deleteUser(User user) {
        log.info("User deleted from LinkedList");
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
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.set(i, user);
            }
        }
        return user;
    }
}
