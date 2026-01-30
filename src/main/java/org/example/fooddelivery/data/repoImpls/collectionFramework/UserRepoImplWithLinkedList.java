package org.example.fooddelivery.data.repoImpls.collectionFramework;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.repo.UserRepo;

import org.springframework.stereotype.Repository;

import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicLong;

@Repository("URwLL")
@Slf4j
public class UserRepoImplWithLinkedList implements UserRepo {
    private final LinkedList<IUser> users = new LinkedList<>();
    private final AtomicLong nextId = new AtomicLong(1);
    @Override
    public IUser saveUser(IUser user) {
        user.setId(nextId.getAndIncrement());
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
