package org.example.fooddelivery.data.repoImpls.namedParamJdbcTemplate;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@RequiredArgsConstructor
@Repository("ORwNJT")
public class OrderRepoImpl implements OrderRepo {

    private final NamedParameterJdbcTemplate template;

    @Transactional
    @Override
    public IOrder saveOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sqlOrder = "INSERT INTO orders(order_date, status, user_id, total_price) " +
                "VALUES (:order_date, :status, :user_id, :total_price)";

        SqlParameterSource paramsOrder = new MapSqlParameterSource()
                .addValue("order_date", Timestamp.valueOf(order.getOrderDate()))
                .addValue("status", order.getStatus().name())
                .addValue("user_id", order.getUser().getId())
                .addValue("total_price", order.getTotalPrice());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = template.update(
                sqlOrder, paramsOrder, keyHolder, new String[]{"id"});
        if (affectedRow == 0) throw new RuntimeException("Failed to save order");
        order.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        String sqlOrderMenuItems = "INSERT INTO orders_menu_items(order_id, menu_item_id) " +
                "VALUES (:order_id, :menu_item_id)";

        SqlParameterSource[] batchArgs = new SqlParameterSource[order.getItemList().size()];
        int index = 0;
        for (IMenuItem item : order.getItemList()) {
            batchArgs[index++] = new MapSqlParameterSource()
                    .addValue("order_id", order.getId())
                    .addValue("menu_item_id", item.getId());
        }
        template.batchUpdate(sqlOrderMenuItems, batchArgs);
        return order;
    }

    @Transactional
    @Override
    public IOrder updateOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sql = "UPDATE orders SET order_date = :order_date, status = :status, " +
                "user_id = :user_id, total_price = :total_price WHERE id = :id";

        int affectedRow = template.update(sql, new MapSqlParameterSource()
                .addValue("order_date", Timestamp.valueOf(order.getOrderDate()))
                .addValue("status", order.getStatus().name())
                .addValue("user_id", order.getUser().getId())
                .addValue("total_price", order.getTotalPrice())
                .addValue("id", order.getId()));

        if (affectedRow == 0) throw new RuntimeException("Failed to update Order");
        return order;
    }

    @Transactional
    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = :status WHERE id = :id";
        int affectedRow = template.update(sql, new MapSqlParameterSource()
                .addValue("status", status.name())
                .addValue("id", orderId));
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
                    WHERE o.user_id = :user_id
                    ORDER BY o.id
                """;
        return template.query(
                sql,
                new MapSqlParameterSource("user_id", user.getId()),
                (ResultSetExtractor<List<IOrder>>) rs -> {
                    Map<Long, IOrder> orderMap = new LinkedHashMap<>();
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
                });
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
                    WHERE o.status = :order_status
                    ORDER BY o.id
                """;
        return template.query(
                sql,
                new MapSqlParameterSource("order_status",status.name()),
                (rs) -> {
                    Map<Long, IOrder> orderMap = new LinkedHashMap<>();
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
                });
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
                    WHERE o.id = :order_id
                    ORDER BY o.id
                """;

        return template.query(sql,
                new MapSqlParameterSource("order_id", orderId),
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
                });
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
                .menuCategory(MenuCategory.valueOf(rs.getString("menu_category")))
                .price(rs.getBigDecimal("menu_item_price"))
                .build();
    }
}
