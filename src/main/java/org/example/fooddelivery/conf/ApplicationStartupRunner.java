package org.example.fooddelivery.conf;

import org.example.fooddelivery.domain.model.MenuItem;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static org.example.fooddelivery.domain.model.MenuCategory.*;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {
    private final MenuItemRepo menuItemRepo;

    public ApplicationStartupRunner(@Qualifier("MRwPS") MenuItemRepo menuItemRepo) {
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public void run(String... args) {
        menuItemRepo.saveMenuItem(new MenuItem(1L,
                "Шашлык из свинины",
                MainMenu,
                BigDecimal.valueOf(240)));
        menuItemRepo.saveMenuItem(new MenuItem(2L,
                "Шашлык из говядины",
                MainMenu,
                BigDecimal.valueOf(320)));
        menuItemRepo.saveMenuItem(new MenuItem(3L,
                "Шашлык из баранины",
                MainMenu,
                BigDecimal.valueOf(320)));

        menuItemRepo.saveMenuItem(new MenuItem(100L,
                "Кофе",
                DrinksMenu,
                BigDecimal.valueOf(50)));
        menuItemRepo.saveMenuItem(new MenuItem(101L,
                "Чай",
                DrinksMenu,
                BigDecimal.valueOf(50)));

        menuItemRepo.saveMenuItem(new MenuItem(200L,
                "Кетчуп",
                SaucesMenu,
                BigDecimal.valueOf(30)));
        menuItemRepo.saveMenuItem(new MenuItem(201L,
                "Сырный",
                SaucesMenu,
                BigDecimal.valueOf(30)));
        menuItemRepo.saveMenuItem(new MenuItem(202L,
                "Чесночный",
                SaucesMenu,
                BigDecimal.valueOf(30)));
        menuItemRepo.saveMenuItem(new MenuItem(203L,
                "Кисло-Сладкий",
                SaucesMenu,
                BigDecimal.valueOf(30)));
    }
}
