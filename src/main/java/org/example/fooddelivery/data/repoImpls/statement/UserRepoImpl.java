package org.example.fooddelivery.data.repoImpls.statement;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@RequiredArgsConstructor
@Repository("URwPS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    @Override
    public IUser saveUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(?,?,?,?,?,?);";

        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(false);

            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                setUserParameters(user, ps);

                int affectedRow = ps.executeUpdate();
                if (affectedRow == 0) throw new SQLException("Failed to save user");

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        user.setId(generatedKeys.getLong(1));
                    }
                }

                connection.commit();
                return user;
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException(rollbackEx);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save user", e);
        }
    }

    public static void setUserParameters(IUser user, PreparedStatement ps) throws SQLException {
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getPassword());
        ps.setString(4, user.getTelegram());
        ps.setString(5, user.getPhone());
        ps.setString(6, user.getAddress());
    }

    @Override
    public IUser updateUser(IUser user) {
        String sql = "UPDATE users SET name=?, email=?, password=?, telegram=?, phone=?, address=? WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            setUserParameters(user, ps);
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
        return executeQueryAndBuildUser(sql, email);
    }

    @Override
    public void deleteUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "DELETE FROM users WHERE id=?";

        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, user.getId());
                int affectedRow = ps.executeUpdate();
                if (affectedRow == 0) throw new SQLException("Failed to delete user");
                conn.commit();
            } catch (SQLException e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackEx) {
                    throw new RuntimeException(rollbackEx);
                }
                throw e;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    public IUser getUserById(Long id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        return executeQueryAndBuildUser(sql, id);
    }

    private IUser executeQueryAndBuildUser(String sql, Object param) {
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            if (param instanceof String) {
                ps.setString(1, (String) param);
            } else if (param instanceof Long) {
                ps.setLong(1, (Long) param);
            } else {
                throw new IllegalArgumentException("Unsupported parameter type: " + param.getClass());
            }

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
            throw new RuntimeException("Failed to get user", e);
        }
        return null;
    }
}
