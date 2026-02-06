package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
@Repository("ORwJT")
public class OrderRepoImpl implements OrderRepo {

    private final JdbcTemplate jdbcTemplate;

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
        String sql = """
                    SELECT
                        o.id AS order_id, o.order_date, o.status, o.total_price,
                        u.id AS user_id, u.name AS user_name, u.email, u.password, u.telegram, u.phone, u.address,
                        mi.id AS menu_item_id, mi.name AS menu_item_name, mi.menu_category, mi.price AS menu_item_price
                    FROM orders o
                    JOIN users u ON o.user_id = u.id
                    JOIN orders_menu_items omi ON o.id = omi.order_id
                    JOIN menu_items mi ON omi.menu_item_id = mi.id
                    WHERE o.user_id = ?
                    ORDER BY o.id
                """;
        return jdbcTemplate.query(sql,
                (rs) -> {
                    HashMap<Long, IOrder> orderMap = new LinkedHashMap<>();
                    while (rs.next()) {
                        Long orderId = rs.getLong("order_id");
                        IOrder order = orderMap.computeIfAbsent(orderId, id -> {
                            try {
                                IOrder newOrder = createOrderFromRs(rs);
                                newOrder.setUser(createUserFromRS(rs));
                                newOrder.setItemList(new ArrayList<>());
                                return newOrder;
                            } catch (SQLException e) {
                                throw new RuntimeException("Failed to get order by status");
                            }
                        });
                        order.getItemList().add(createMenuItemFromRS(rs));
                    }
                    return new ArrayList<>(orderMap.values());
                },
                user.getId());
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        String sql = """
                    SELECT
                        o.id AS order_id, o.order_date, o.status, o.total_price,
                        u.id AS user_id, u.name AS user_name, u.email, u.password, u.telegram, u.phone, u.address,
                        mi.id AS menu_item_id, mi.name AS menu_item_name, mi.menu_category, mi.price AS menu_item_price
                    FROM orders o
                    JOIN users u ON o.user_id = u.id
                    JOIN orders_menu_items omi ON o.id = omi.order_id
                    JOIN menu_items mi ON omi.menu_item_id = mi.id
                    WHERE o.status = ?
                    ORDER BY o.id
                """;
        return jdbcTemplate.query(sql,
                (rs) -> {
                    HashMap<Long, IOrder> orderMap = new LinkedHashMap<>();
                    while (rs.next()) {
                        Long orderId = rs.getLong("order_id");
                        IOrder order = orderMap.computeIfAbsent(orderId, id -> {
                            try {
                                IOrder newOrder = createOrderFromRs(rs);
                                newOrder.setUser(createUserFromRS(rs));
                                newOrder.setItemList(new ArrayList<>());
                                return newOrder;
                            } catch (SQLException e) {
                                throw new RuntimeException("Failed to get order by status");
                            }
                        });
                        order.getItemList().add(createMenuItemFromRS(rs));
                    }
                    return new ArrayList<>(orderMap.values());
                },
                status.name());
    }

    protected IOrder getOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = """
                    SELECT
                        o.id AS order_id, o.order_date, o.status, o.total_price,
                        u.id AS user_id, u.name AS user_name, u.email, u.password, u.telegram, u.phone, u.address,
                        mi.id AS menu_item_id, mi.name AS menu_item_name, mi.menu_category, mi.price AS menu_item_price
                    FROM orders o
                    JOIN users u ON o.user_id = u.id
                    JOIN orders_menu_items omi ON o.id = omi.order_id
                    JOIN menu_items mi ON omi.menu_item_id = mi.id
                    WHERE o.id = ?
                    ORDER BY o.id
                """;

        return jdbcTemplate.query(sql,
                (rs) -> {
                    IOrder order = null;
                    while (rs.next()) {
                        if (order == null) {
                            order = createOrderFromRs(rs);
                            order.setUser(createUserFromRS(rs));
                            order.setItemList(new ArrayList<>());
                        }
                        order.getItemList().add(createMenuItemFromRS(rs));
                    }
                    return order;
                },
                orderId);
    }

    private IOrder createOrderFromRs(ResultSet rs) throws SQLException {
        return Order.builder()
                .id(rs.getLong("order_id"))
                .orderDate(rs.getTimestamp("order_date").toLocalDateTime())
                .status(OrderStatus.valueOf(rs.getString("status")))
                .totalPrice(rs.getBigDecimal("total_price"))
                .build();
    }

    private IUser createUserFromRS(ResultSet rs) throws SQLException {
        return User.builder()
                .id(rs.getLong("user_id"))
                .name(rs.getString("user_name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .telegram(rs.getString("telegram"))
                .phone(rs.getString("phone"))
                .address(rs.getString("address"))
                .build();
    }

    private IMenuItem createMenuItemFromRS(ResultSet rs) throws SQLException {
        return MenuItem.builder()
                .id(rs.getLong("menu_item_id"))
                .name(rs.getString("menu_item_name"))
                .category(MenuCategory.valueOf(rs.getString("menu_category")))
                .price(rs.getBigDecimal("menu_item_price"))
                .build();
    }
}
