package org.example.fooddelivery.data.repoImpls.collectionFramework;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;

@Repository("URwLL")
@Slf4j
public class UserRepoImplWithLinkedList implements UserRepo {
    private final LinkedList<IUser> users = new LinkedList<>();
    @Override
    public IUser saveUser(IUser user) {
        users.add(user);
        log.info("User created with LinkedList");
        return user;
    }

    @Override
    public void deleteUser(IUser user) {
        log.info("User deleted from LinkedList");
        users.remove(user);
    }

    @Override
    public IUser getUserByEmail(String email) {
        return users.stream()
                    .filter(user -> user.getEmail().equals(email))
                    .findFirst()
                    .orElse(null);
    }

    @Override
    public IUser updateUser(IUser user) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).equals(user)) {
                users.set(i, user);
            }
        }
        return user;
    }
}
