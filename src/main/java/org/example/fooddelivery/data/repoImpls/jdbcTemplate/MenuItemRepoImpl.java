package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository("MRwJT")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "INSERT INTO menu_items (id, name, menu_category, price) VALUES (?, ?, ?, ?) " +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name = excluded.name, menu_category = excluded.menu_category ,price = excluded.price";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                (Connection con) -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
                    ps.setLong(1, menuItem.getId());
                    ps.setString(2, menuItem.getName());
                    ps.setString(3, menuItem.getMenuCategory().name());
                    ps.setBigDecimal(4, menuItem.getPrice());
                    return ps;
                }, keyHolder);

        menuItem.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return menuItem;
    }

    @Transactional
    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "UPDATE menu_items SET name = ?, menu_category = ?, price = ? WHERE id = ?";

        jdbcTemplate.update(sql, menuItem.getName(), menuItem.getMenuCategory().name(), menuItem.getPrice(), menuItem.getId());
        return menuItem;
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = "SELECT * FROM menu_items WHERE id = ?";

        return jdbcTemplate.queryForObject(sql,
                new BeanPropertyRowMapper<>(MenuItem.class),
                id
        );
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        String sql = "SELECT * FROM menu_items WHERE menu_category = ?";
        return jdbcTemplate.queryForStream(sql,
                        new BeanPropertyRowMapper<>(MenuItem.class),
                        category.name())
                .map(item -> (IMenuItem) item).toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        String sql = "DELETE FROM menu_items WHERE id = ?";
        jdbcTemplate.update(sql, menuItem.getId());
    }
}
