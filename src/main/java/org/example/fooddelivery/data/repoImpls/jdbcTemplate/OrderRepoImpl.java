package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.example.fooddelivery.domain.repo.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository("ORwJT")
public class OrderRepoImpl implements OrderRepo {

    private final JdbcTemplate jdbcTemplate;
    private final MenuItemRepo menuItemRepo;
    private final UserRepo userRepo;


    public OrderRepoImpl(JdbcTemplate jdbcTemplate,
                         @Qualifier("MRwJT") MenuItemRepo menuItemRepo,
                         @Qualifier("URwJT") UserRepo userRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.menuItemRepo = menuItemRepo;
        this.userRepo = userRepo;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sqlOrder = "INSERT INTO orders(order_date, status, user_id, total_price) VALUES (?, ?, ?, ?)";
        String sqlOrderMenuItems = "INSERT INTO orders_menu_items(order_id, menu_item_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = jdbcTemplate.update(
                con -> {
                    PreparedStatement ps = con.prepareStatement(sqlOrder, new String[]{"id"});
                    ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
                    ps.setString(2, order.getStatus().name());
                    ps.setLong(3, order.getUser().getId());
                    ps.setBigDecimal(4, order.getTotalPrice());
                    return ps;
                }, keyHolder);

        if (affectedRow == 0) throw new RuntimeException("Failed to save order");

        order.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        List<Object[]> batchArgs = new ArrayList<>();
        for (IMenuItem item : order.getItemList()) {
            batchArgs.add(new Object[]{order.getId(), item.getId()});
        }
        jdbcTemplate.batchUpdate(sqlOrderMenuItems, batchArgs);
        return order;
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sql = "UPDATE orders SET order_date=?, status = ?, user_id = ?, total_price = ? WHERE id = ?";

        int affectedRow = jdbcTemplate.update(sql,
                Timestamp.valueOf(order.getOrderDate()),
                order.getStatus().name(),
                order.getUser().getId(),
                order.getTotalPrice(),
                order.getId());

        if (affectedRow == 0) throw new RuntimeException("Failed to update Order");
        return order;
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        int affectedRow = jdbcTemplate.update(sql, status.name(), orderId);
        if (affectedRow == 0) throw new RuntimeException("Failed to update OrderStatus");
        return getOrderById(orderId);
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "SELECT * FROM orders WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, numRow) -> getOrderById(rs.getLong("id")));
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        String sql = "SELECT * FROM orders WHERE status = ?";
        return jdbcTemplate.query(sql,
                (rs, numRow) -> getOrderById(rs.getLong("id")),
                status.name());
    }

    protected IOrder getOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "SELECT * FROM orders WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, (rs, numRow) -> {
            IOrder order = new Order();
            order.setId(rs.getLong("id"));
            order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
            order.setStatus(OrderStatus.valueOf(rs.getString("status")));
            order.setUser(userRepo.getUserById(rs.getLong("user_id")));
            order.setTotalPrice(rs.getBigDecimal("total_price"));
            order.setItemList(getMenuItemsForOrder(order.getId()));
            return order;
        }, orderId);
    }

    private List<IMenuItem> getMenuItemsForOrder(Long orderId) {
        String sql = "SELECT * FROM orders_menu_items WHERE order_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                menuItemRepo.getMenuItemById(rs.getLong("menu_item_id")),
                orderId);
    }
}
