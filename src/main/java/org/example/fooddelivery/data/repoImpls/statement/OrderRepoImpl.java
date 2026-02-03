package org.example.fooddelivery.data.repoImpls.statement;

import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.MenuItemRepo;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository("ORwPS")
public class OrderRepoImpl implements OrderRepo {

    private final DataSource dataSource;
    private final MenuItemRepo menuItemRepo;

    public OrderRepoImpl(DataSource dataSource,@Qualifier("MRwPS") MenuItemRepo menuItemRepo) {
        this.dataSource = dataSource;
        this.menuItemRepo = menuItemRepo;
    }

    @Override
    public IOrder saveOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sqlOrder = "INSERT INTO orders(order_date, status, user_id, total_price) VALUES (?, ?, ?, ?)";
        String sqlOrderMenuItems = "INSERT INTO orders_menu_items(order_id, menu_item_id) VALUES (?, ?)";
        try (PreparedStatement psIntoOrders = dataSource.getConnection().prepareStatement(sqlOrder, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement psIntoOrdersMenuItems = dataSource.getConnection().prepareStatement(sqlOrderMenuItems)
        ) {
            psIntoOrders.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            psIntoOrders.setString(2, order.getStatus().name());
            psIntoOrders.setLong(3, order.getUser().getId());
            psIntoOrders.setBigDecimal(4, order.getTotalPrice());

            int affectedRow = psIntoOrders.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save Order");

            try (ResultSet generatedKeys = psIntoOrders.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }
            }

            for (IMenuItem item : order.getItemList()) {
                psIntoOrdersMenuItems.setLong(1, order.getId());
                psIntoOrdersMenuItems.setLong(2, item.getId());
                psIntoOrdersMenuItems.executeUpdate();
            }

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        String sql = "UPDATE orders SET order_date=?, status = ?, user_id = ?, total_price = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            ps.setString(2, order.getStatus().name());
            ps.setLong(3, order.getUser().getId());
            ps.setBigDecimal(4, order.getTotalPrice());
            ps.setLong(5, order.getId());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update Order");
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, status.name());
            ps.setLong(2, orderId);
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update OrderStatus");
            return getOrderById(orderId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        String sql = "SELECT * FROM orders WHERE user_id = ?";
        List<IOrder> orders = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, user.getId());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long orderId = rs.getLong("id");
                    IOrder order = getOrderById(orderId);
                    orders.add(order);
                }
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        String sql = "SELECT * FROM orders WHERE status = ?";
        List<IOrder> orders = new ArrayList<>();
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, status.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Long orderId = rs.getLong("id");
                    IOrder order = getOrderById(orderId);
                    orders.add(order);
                }
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected IOrder getOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        String sql = "SELECT * FROM orders WHERE id = ?";
        String sqlFromOrdersMenuItems = "SELECT * FROM orders_menu_items WHERE order_id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql);
             PreparedStatement psFromOrdersMenuItems = dataSource.getConnection().prepareStatement(sqlFromOrdersMenuItems)
        ) {
            ps.setLong(1, orderId);
            Order order = new Order();
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order.setId(rs.getLong("id"));
                    order.setOrderDate(rs.getTimestamp("order_date").toLocalDateTime());
                    order.setStatus(OrderStatus.valueOf(rs.getString("status")));
                    order.setUser(getUserById(rs.getLong("user_id")));
                    order.setTotalPrice(rs.getBigDecimal("total_price"));
                }
            }
            psFromOrdersMenuItems.setLong(1, orderId);
            List<IMenuItem> menuItems = new ArrayList<>();
            try (ResultSet rs = psFromOrdersMenuItems.executeQuery()) {
                while (rs.next()) {
                    IMenuItem item = menuItemRepo.getMenuItemById(rs.getLong("menu_item_id"));
                    if (item != null) {
                        menuItems.add(item);
                    }
                }
            }
            order.setItemList(menuItems);
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private IUser getUserById(Long id) {
        if (id == null) throw new IllegalArgumentException("id cannot be null");

        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return User.builder()
                            .id(rs.getLong("id"))
                            .name(rs.getString("name"))
                            .email(rs.getString("email"))
                            .password(rs.getString("password"))
                            .phone(rs.getString("phone"))
                            .telegram(rs.getString("telegram"))
                            .address(rs.getString("address"))
                            .build();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
