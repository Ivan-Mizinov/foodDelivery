package org.example.fooddelivery.data.repoImpls.namedParamJdbcTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Repository("URwNJT")
public class UserRepoImpl implements UserRepo {

    private final NamedParameterJdbcTemplate template;

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "INSERT INTO users(name, email, password, telegram, phone, address) " +
                "VALUES(:name, :email, :password, :telegram, :phone, :address)";

        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(user);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRows = template.update(sql, params, keyHolder, new String[]{"id"});
        if (affectedRows == 0) throw new RuntimeException("Failed to save user");
        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET name= :name, email= :email, password= :password, " +
                "telegram= :telegram, phone= :phone, address= :address WHERE id = :id";
        int affectedRow = template.update(sql, new BeanPropertySqlParameterSource(user));
        if (affectedRow == 0) throw new IllegalArgumentException("Failed to update user");
        return user;
    }

    @Override
    public IUser getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.warn("Invalid user email: {}", email);
            return null;
        }

        String sql = "SELECT * FROM users WHERE email = :email";

        try {
            return template.queryForObject(sql,
                    new MapSqlParameterSource("email", email),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("User not found for email: {}", email);
            return null;
        } catch (DataAccessException e) {
            log.error("Database error while fetching user by email: {}", email, e);
            throw e;
        }
    }

    @Override
    public IUser getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = :id";

        try {
            return template.queryForObject(sql,
                    new MapSqlParameterSource("id", id),
                    new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            log.debug("User not found for ID: {}", id);
            return null;
        } catch (DataAccessException e) {
            log.error("Database error while fetching user by ID: {}", id, e);
            throw e;
        }
    }

    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "DELETE FROM users WHERE id = :id";
        int affectedRow = template.update(sql,
                new MapSqlParameterSource("id", user.getId()));
        if (affectedRow == 0) throw new IllegalArgumentException("Failed to delete user");
    }
}
