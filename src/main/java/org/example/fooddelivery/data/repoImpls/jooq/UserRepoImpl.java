package org.example.fooddelivery.data.repoImpls.jooq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.example.fooddelivery.generated.jooq.tables.records.UsersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.Objects;

import static org.example.fooddelivery.generated.jooq.Tables.USERS;

@Slf4j
@RequiredArgsConstructor
@Repository("URwJooq")
public class UserRepoImpl implements UserRepo {

    private final DSLContext dslContext;

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        UsersRecord record = dslContext.insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.TELEGRAM, user.getTelegram())
                .set(USERS.PHONE, user.getPhone())
                .set(USERS.ADDRESS, user.getAddress())
                .returning(USERS.ID)
                .fetchOne();

        user.setId(Objects.requireNonNull(record).getId());
        return user;
    }

    @Override
    public IUser updateUser(IUser user) {
        int affectedRow = dslContext.update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.TELEGRAM, user.getTelegram())
                .set(USERS.PHONE, user.getPhone())
                .set(USERS.ADDRESS, user.getAddress())
                .where(USERS.ID.eq(user.getId()))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to update user");
        return user;
    }

    @Override
    public IUser getUserByEmail(String email) {
        return dslContext.selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOneInto(User.class);
    }

    public IUser getUserById(Long id) {
        return dslContext.selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOneInto(User.class);
    }

    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");
        int affectedRow = dslContext.deleteFrom(USERS)
                .where(USERS.ID.eq(user.getId()))
                .execute();
        if (affectedRow == 0) throw new IllegalArgumentException("Failed to delete user");
    }
}
