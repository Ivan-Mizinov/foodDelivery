package org.example.fooddelivery.presentation.service;

import org.example.fooddelivery.domain.interractor.MenuItemInterractor;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class MenuItemService extends MenuItemInterractor {
    public MenuItemService(@Qualifier("MenuItemRepoAdapter_JPA") MenuItemRepo repo) {
        super(repo);
    }
}
