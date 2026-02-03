package org.example.fooddelivery.data.repoImpls.collectionFramework;

import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;

import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository("URwAL")
@Slf4j
public class UserRepoImpl implements UserRepo {
    private final List<IUser> users = new ArrayList<>();
    private final AtomicLong nextId = new AtomicLong(1);

    @Override
    public IUser saveUser(IUser user) {
        user.setId(nextId.getAndIncrement());
        users.add(user);
        log.info("User created with ArrayList");
        return user;
    }

    @Override
    public void deleteUser(IUser user) {
        log.info("User deleted with ArrayList");
        users.remove(user);
    }

    @Override
    public IUser getUserById(Long id) {
        return users.stream()
                .filter(user -> user.getId().equals(id))
                .findFirst()
                .orElse(null);
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
        int index = users.indexOf(user);
        if (index != -1) users.set(index, user);
        return user;
    }
}
