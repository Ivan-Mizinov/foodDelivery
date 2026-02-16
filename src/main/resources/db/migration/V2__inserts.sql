INSERT INTO users (id, name, email, password, telegram, phone, address)
VALUES (999999999, 'Ivan_Mizinov', 'Ivan@com', '$2a$10$Rwd2A.tXDnFGs/s5oxcrw.ZissYoC00/vxsDyUyNlCjXROFghla/y',
        '@Ivan', 85551234567, 'г. Москва, ул. Тверская, д. 15')
ON CONFLICT (id) DO NOTHING;

INSERT INTO menu_items(id, name, menu_category, price) VALUES
(1, 'Шашлык из свинины', 'MainMenu', 240),
(2, 'Шашлык из говядины', 'MainMenu', 320),
(3, 'Шашлык из баранины', 'MainMenu', 320),
(4, 'Картофель Фри', 'MainMenu', 200),
(5, 'Картофельное пюре', 'MainMenu', 180),
(6, 'Рис на пару', 'MainMenu', 170),
(100, 'Кофе', 'DrinksMenu', 50),
(101, 'Чай', 'DrinksMenu', 50),
(200, 'Кетчуп', 'SaucesMenu', 30),
(201, 'Майонез', 'SaucesMenu', 30),
(202, 'Сырный', 'SaucesMenu', 30),
(203, 'Чесночный', 'SaucesMenu', 30),
(204, 'Кисло-Сладкий', 'SaucesMenu', 30)
ON CONFLICT (id) DO NOTHING;