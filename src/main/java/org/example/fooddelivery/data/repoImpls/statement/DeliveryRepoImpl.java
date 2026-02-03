package org.example.fooddelivery.data.repoImpls.statement;

import org.example.fooddelivery.domain.model.Delivery;
import org.example.fooddelivery.domain.model.IDelivery;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository("DRwPS")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final DataSource dataSource;
    private final OrderRepoImpl orderRepo;

    public DeliveryRepoImpl(DataSource dataSource, @Qualifier("ORwPS") OrderRepoImpl orderRepo) {
        this.dataSource = dataSource;
        this.orderRepo = orderRepo;
    }

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        if (delivery == null) throw new IllegalArgumentException("delivery cannot be null");

        String sql = "INSERT INTO deliveries(address, phone, delivery_time, order_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDeliveryTime()));
            ps.setLong(4, delivery.getOrder().getId());

            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to save Delivery");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    delivery.setId(rs.getLong(1));
                }
            }
            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        String sql = "UPDATE deliveries SET address=?, phone=?, delivery_time=?, order_id=? WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setString(1, delivery.getAddress());
            ps.setString(2, delivery.getPhone());
            ps.setTimestamp(3, Timestamp.valueOf(delivery.getDeliveryTime()));
            ps.setLong(4, delivery.getOrder().getId());
            ps.setLong(5, delivery.getId());
            int affectedRow = ps.executeUpdate();
            if (affectedRow == 0) throw new SQLException("Failed to update Delivery");
            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IDelivery getDeliveryById(Long id) {
        String sql = "SELECT * FROM deliveries WHERE id=?";
        try (PreparedStatement ps = dataSource.getConnection().prepareStatement(sql)) {
            ps.setLong(1, id);
            IDelivery delivery = new Delivery();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    delivery.setId(rs.getLong("id"));
                    delivery.setAddress(rs.getString("address"));
                    delivery.setPhone(rs.getString("phone"));
                    delivery.setDeliveryTime(rs.getTimestamp("delivery_time").toLocalDateTime());
                    delivery.setOrder(orderRepo.getOrderById(rs.getLong("order_id")));
                }
            }
            return delivery;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
