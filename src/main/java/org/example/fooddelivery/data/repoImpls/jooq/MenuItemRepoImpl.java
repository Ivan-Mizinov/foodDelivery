package org.example.fooddelivery.data.repoImpls.jooq;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.IMenuItem;
import org.example.fooddelivery.domain.model.MenuCategory;
import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.example.fooddelivery.generated.jooq.Tables.MENU_ITEMS;

@RequiredArgsConstructor
@Repository("MRwJooq")
public class MenuItemRepoImpl implements MenuItemRepo {

    private final DSLContext dslContext;

    @Override
    public IMenuItem saveMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");

        int affectedRow = dslContext.insertInto(MENU_ITEMS)
                .set(MENU_ITEMS.ID, menuItem.getId())
                .set(MENU_ITEMS.NAME, menuItem.getName())
                .set(MENU_ITEMS.MENU_CATEGORY, menuItem.getMenuCategory().name())
                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
                .onDuplicateKeyUpdate()
                .set(MENU_ITEMS.NAME, menuItem.getName())
                .set(MENU_ITEMS.MENU_CATEGORY, menuItem.getMenuCategory().name())
                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
                .execute();

        if (affectedRow == 0) throw new RuntimeException("Failed to save menuItem");

        return menuItem;
    }

    @Override
    public IMenuItem updateMenuItem(IMenuItem menuItem) {
        if (menuItem == null) throw new IllegalArgumentException("menuItem cannot be null");
        int affectedRow = dslContext.update(MENU_ITEMS)
                .set(MENU_ITEMS.NAME, menuItem.getName())
                .set(MENU_ITEMS.MENU_CATEGORY, menuItem.getMenuCategory().name())
                .set(MENU_ITEMS.PRICE, menuItem.getPrice())
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to update menuItem");
        return menuItem;
    }

    @Override
    public IMenuItem getMenuItemById(Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");
        return dslContext.selectFrom(MENU_ITEMS)
                .where(MENU_ITEMS.ID.eq(id))
                .fetchOneInto(MenuItem.class);
    }

    @Override
    public List<IMenuItem> getMenuItemsByCategory(MenuCategory category) {
        return dslContext.selectFrom(MENU_ITEMS)
                .where(MENU_ITEMS.MENU_CATEGORY.eq(category.name()))
                .fetchInto(MenuItem.class);
    }

    @Override
    public void deleteMenuItem(IMenuItem menuItem) {
        int affectedRow = dslContext.deleteFrom(MENU_ITEMS)
                .where(MENU_ITEMS.ID.eq(menuItem.getId()))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to delete menuItem");
    }
}
