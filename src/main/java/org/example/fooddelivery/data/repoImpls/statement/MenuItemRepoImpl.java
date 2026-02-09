package org.example.fooddelivery.data.repoImpls.statement;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository("MRwPS")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final DataSource dataSource;

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "INSERT INTO menu_items (id, name, menu_category, price) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name = excluded.name, menu_category = excluded.menu_category ,price = excluded.price";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, menuItem.getId());
            ps.setString(2, menuItem.getName());
            ps.setString(3, menuItem.getMenuCategory().name());
            ps.setBigDecimal(4, menuItem.getPrice());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save MenuItem");

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    menuItem.setId(generatedKeys.getLong(1));
                }
            }
            return menuItem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "UPDATE menu_items SET name = ?, menu_category = ?, price = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, menuItem.getName());
            ps.setString(2, menuItem.getMenuCategory().name());
            ps.setBigDecimal(3, menuItem.getPrice());
            ps.setLong(4, menuItem.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update MenuItem");
            return menuItem;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = "SELECT * FROM menu_items WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return MenuItem.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .menuCategory(MenuCategory.valueOf(rs.getString("menu_category")))
                            .price(rs.getBigDecimal("price"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        String sql = "SELECT * FROM menu_items WHERE menu_category = ?";
        List<IMenuItem> menuItems = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, category.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    menuItems.add(MenuItem.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .menuCategory(MenuCategory.valueOf(rs.getString("menu_category")))
                            .price(rs.getBigDecimal("price"))
                            .build());
                }
            }

            return menuItems;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, menuItem.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to delete MenuItem");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
