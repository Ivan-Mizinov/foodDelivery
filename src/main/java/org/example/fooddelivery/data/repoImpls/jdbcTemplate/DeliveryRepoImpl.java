package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

@RequiredArgsConstructor
@Repository("DRwJT")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        if (delivery == null) throw new IllegalArgumentException("delivery cannot be null");

        String sql = "INSERT INTO deliveries(address, phone, delivery_time, order_id) VALUES (?,?,?,?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        int affectedRow = jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDeliveryTime()));
            ps.setLong(4, delivery.getOrder().getId());
            return ps;
        }, keyHolder);

        if (affectedRow == 0) throw new RuntimeException("Failed to save delivery");

        delivery.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return delivery;
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        String sql = "UPDATE deliveries SET address=?, phone=?, delivery_time=?, order_id=? WHERE id=?";
        int affectedRow = jdbcTemplate.update(sql, delivery.getAddress(),
                delivery.getPhone(), delivery.getDeliveryTime(), delivery.getOrder().getId());

        if (affectedRow == 0) throw new RuntimeException("Failed to update delivery");

        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        String sql = """
                SELECT
                	d.id AS delivery_id, d.delivery_time, d.order_id,
                	o.order_date, o.status, o.user_id, o.total_price,
                	u.name AS user_name, u.email, u.password, u.telegram, u.phone, u.address,
                	omi.menu_item_id,
                	mi.name AS menu_item_name, mi.menu_category, mi.price
                FROM deliveries d
                JOIN orders o ON d.order_id = o.id
                JOIN users u ON u.id = o.user_id
                JOIN orders_menu_items omi ON o.id = omi.order_id
                JOIN menu_items mi ON omi.menu_item_id = mi.id
                WHERE d.id = ?
                ORDER BY d.id
                """;
        return jdbcTemplate.query(sql,
                (ResultSet rs) -> {
                    IDelivery delivery = null;
                    while (rs.next()) {
                        if (delivery == null) {
                            delivery = createDeliveryFromRS(rs);
                            delivery.setOrder(createOrderFromRS(rs));
                            delivery.getOrder().setUser(createUserFromRS(rs));
                            delivery.getOrder().setItemList(new ArrayList<>());
                        }
                        delivery.getOrder().getItemList().add(createMenuItemFromRS(rs));
                    }
                    if (delivery == null) throw new RuntimeException("Failed to get delivery with id " + id);
                    return delivery;
                }, id);
    }

    private IDelivery createDeliveryFromRS(ResultSet rs) throws SQLException {
        return Delivery.builder()
                .id(rs.getLong("delivery_id"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .deliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime())
                .build();
    }

    private IOrder createOrderFromRS(ResultSet rs) throws SQLException {
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
                .phone(rs.getString("user_phone"))
                .address(rs.getString("user_address"))
                .build();
    }

    private IMenuItem createMenuItemFromRS(ResultSet rs) throws SQLException {
        return MenuItem.builder()
                .id(rs.getLong("menu_item_id"))
                .name(rs.getString("menu_item_name"))
                .category(MenuCategory.valueOf(rs.getString("menu_category")))
                .price(rs.getBigDecimal("price"))
                .build();
    }
}
