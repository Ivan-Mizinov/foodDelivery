package org.example.fooddelivery.data.repoImpls.statement;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IUser;
import org.example.fooddelivery.domain.model.User;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
@Repository("URwS")
public class UserRepoImpl implements UserRepo {

    private final DataSource dataSource;

    @Override
    public IUser saveUser(IUser user) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            String sql = "INSERT INTO users(name, email, password, telegram, phone, address) VALUES(" +
                    "'" + user.getName() + "', '" + user.getEmail() + "', '" + user.getPassword() + "', '" +
                    user.getTelegram() + "', '" + user.getPhone() + "', '" + user.getAddress() + "'" +
                    ");";
            statement.executeQuery(sql);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public IUser updateUser(IUser user) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            String sql = "UPDATE users SET name='" + user.getName() + "', '" +
                    "email='" + user.getEmail() + "', '" +
                    "password='" + user.getPassword() + "', '" +
                    "telegram='" + user.getTelegram() + "', '" +
                    "phone='" + user.getPhone() + "', '" +
                    "address='" + user.getAddress() + "'" +
                    "WHERE id=" + user.getId() + ";";
            statement.executeUpdate(sql);
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public IUser getUserByEmail(String email) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            String sql = "SELECT * FROM users WHERE email = '" + email + "';";
            ResultSet rs = statement.executeQuery(sql);
            User user = new User();
            while (rs.next()) {
                user = User.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .email(rs.getString("email"))
                        .password(rs.getString("password"))
                        .telegram(rs.getString("telegram"))
                        .phone(rs.getString("phone"))
                        .address(rs.getString("address"))
                        .build();
            }
            return user;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public void deleteUser(IUser user) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            String sql = "DELETE FROM users WHERE id=" + user.getId();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
