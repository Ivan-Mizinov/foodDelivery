package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Repository("URwJT")
public class UserRepoImpl implements UserRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            return ps;
        }, keyHolder);

        user.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return user;
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET name=?, email=?, password=?, telegram=?, phone=?, address=? WHERE id=?";
        int affectedRow = jdbcTemplate.update(sql, user.getName(), user.getEmail(), user.getPassword(), user.getTelegram(), user.getPhone(), user.getAddress(), user.getId());
        if (affectedRow == 0) throw new IllegalArgumentException("Failed to update user");
        return user;
    }

    @Override
    public IUser getUserByEmail(String email) {
        if (email == null || email.isEmpty()) {
            log.warn("Invalid user email: {}", email);
            return null;
        }

        String sql = "SELECT * FROM users WHERE email = ?";

        try {
            return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), email);
        } catch (EmptyResultDataAccessException e) {
            log.debug("User not found for email: {}", email);
            return null;
        } catch (DataAccessException e) {
            log.error("Database error while fetching user by email: {}", email, e);
            throw e;
        }

        /* 1) queryForObject
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .telegram(rs.getString("telegram"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .build(),
                email);
        */

        /* 2) query(sql, ParamArgs, ResultExtractor)
        return jdbcTemplate.query(sql, new Object[]{email}, (ResultSetExtractor<IUser>) rs -> {
            if (rs.next()) {
                return User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .telegram(rs.getString("telegram"))
                        .phone(rs.getString("phone"))
                        .address(rs.getString("address"))
                        .build();
            }
            return null;
        });
        */

        /* 3) query(sql, RowMapper)
        return jdbcTemplate.query(sql, (RowMapper<IUser>) (rs, rowNum) -> {
            if (rs.next()) {
                return User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .telegram(rs.getString("telegram"))
                        .phone(rs.getString("phone"))
                        .address(rs.getString("address"))
                        .build();
            }
            return null;
        }).stream().findFirst().orElse(null);
        */

        /* 4) query(sql, BeanPropertyRowMapper, param)
        return jdbcTemplate.query(sql,
                new BeanPropertyRowMapper<>(User.class), email)
                .stream().findFirst().orElse(null);
        */

        /* 5) queryForStream(sql, BeanPropertyRowMapper, param)

        return jdbcTemplate.queryForStream(sql,
                        new BeanPropertyRowMapper<>(User.class),
                        email)
                .findFirst().orElse(null);
         */
    }

    @Override
    public IUser getUserById(Long id) {
        if (id == null || id <= 0) {
            log.warn("Invalid user ID: {}", id);
            return null;
        }

        String sql = "SELECT * FROM users WHERE id = ?";

        try {
            return jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(User.class), id);
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

        String sql = "DELETE FROM users WHERE id=?";
        int affectedRow = jdbcTemplate.update(sql, user.getId());
        if (affectedRow == 0) throw new IllegalArgumentException("Failed to delete user");
    }
}
