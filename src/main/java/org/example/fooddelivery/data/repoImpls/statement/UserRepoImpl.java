package org.example.fooddelivery.data.repoImpls.statement;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
@Repository("URwPS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?);";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save user");

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getLong(1));
                }
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET name=?, email=?, password=?, telegram=?, phone=?, address=? WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getTelegram());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());
            ps.setLong(7, user.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update user");
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IUser getUserByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by email", e);
        }
        return null;
    }

    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "DELETE FROM users WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, user.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to delete user");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public IUser getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
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
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get user by email", e);
        }
        return null;
    }

}
