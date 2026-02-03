package org.example.fooddelivery.data.repoImpls.jdbcTemplate;

import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Objects;

@Repository("DRwJT")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final JdbcTemplate jdbcTemplate;
    private final OrderRepoImpl orderRepo;

    public DeliveryRepoImpl(JdbcTemplate jdbcTemplate, @Qualifier("ORwJT") OrderRepoImpl orderRepo) {
        this.jdbcTemplate = jdbcTemplate;
        this.orderRepo = orderRepo;
    }

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
        String sql = "SELECT * FROM deliveries WHERE id=?";
        return jdbcTemplate.queryForObject(sql, (rs, numRow) -> Delivery.builder()
                        .id(rs.getLong("id"))
                        .address(rs.getString("address"))
                        .phone(rs.getString("phone"))
                        .deliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime())
                        .order(orderRepo.getOrderById(rs.getLong("order_id")))
                        .build(),
                id);
    }
}
