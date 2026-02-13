package org.example.fooddelivery.data.repoImpls.namedParamJdbcTemplate;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Repository("MRwNJT")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final NamedParameterJdbcTemplate template;

    @Transactional
    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "INSERT INTO menu_items (id, name, menu_category, price) " +
                "VALUES (:id, :name, :menuCategory, :price)" +
                "ON CONFLICT (id) DO " +
                "UPDATE SET name = excluded.name, menu_category = excluded.menu_category ,price = excluded.price";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = template.update(sql,
                new BeanPropertySqlParameterSource(menuItem),
                keyHolder,
                new String[]{"id"});
        if (affectedRow == 0) throw new RuntimeException("Failed to save menuItem");
        menuItem.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return menuItem;
    }

    @Transactional
    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        String sql = "UPDATE menu_items SET name = :name, menu_category = :menu_category, price = :price WHERE id = :id";
        int affectedRow = template.update(sql, new BeanPropertySqlParameterSource(menuItem));
        if (affectedRow == 0) throw new RuntimeException("Failed to update menuItem");
        return menuItem;
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = "SELECT * FROM menu_items WHERE id = :id";

        return template.queryForObject(sql,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(IMenuItem.class)
        );
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        String sql = "SELECT * FROM menu_items WHERE menu_category = :menu_category";
        return template.queryForStream(sql,
                        new MapSqlParameterSource("menu_category", category),
                        new BeanPropertyRowMapper<>(MenuItem.class))
                .map(item -> (IMenuItem) item).toList();
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        String sql = "DELETE FROM menu_items WHERE id = :id";
        int affectedRow = template.update(sql, new MapSqlParameterSource("id", menuItem.getId()));
        if (affectedRow == 0) throw new RuntimeException("Failed to delete menuItem");
    }
}
