package org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository;

import org.example.fooddelivery.data.repoImpls.starter_data_jdbc.crudRepository.entity.OrderEntity;
import org.example.fooddelivery.domain.model.OrderStatus;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("OrderRepoExtCrudRepo")
public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
    @Modifying
    @Query("UPDATE orders SET status = :status WHERE id = :id")
    int updateOrderStatus(@Param("id") Long orderId,
                                  @Param("status") OrderStatus status);

    @Query("SELECT * FROM orders WHERE user_id = :user_id")
    List<OrderEntity> getByUserId(@Param("user_id") Long userId);

    @Query("SELECT * FROM orders WHERE status = :status")
    List<OrderEntity> getOrdersByStatus(@Param("status") OrderStatus status);

    @Modifying
    @Query("INSERT INTO orders_menu_items (order_id, menu_item_id) VALUES (:order_id, :menu_item_id)")
    void insertToOrdersMenuItems(@Param("order_id") Long orderId,
                                 @Param("menu_item_id") Long meneItemId);

    @Query("SELECT menu_item_id FROM orders_menu_items WHERE order_id = :order_id")
    List<Long> getMenuItemsIdsByOrderId(@Param("order_id") Long orderId);

    @Modifying
    @Query("DELETE FROM orders_menu_items WHERE order_id = :order_id")
    void deleteMenuItemsIdByOrderId(@Param("order_id") Long orderId);
}
