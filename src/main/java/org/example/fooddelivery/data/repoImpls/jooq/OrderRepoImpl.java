package org.example.fooddelivery.data.repoImpls.jooq;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.OrderRepo;
import org.example.fooddelivery.generated.jooq.tables.records.OrdersMenuItemsRecord;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.InsertSetMoreStep;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.example.fooddelivery.generated.jooq.Tables.*;

@RequiredArgsConstructor
@Repository("ORwJooq")
public class OrderRepoImpl implements OrderRepo {

    private final DSLContext dslContext;

    @Override
    public IOrder saveOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        Long orderId = Objects.requireNonNull(dslContext.insertInto(ORDERS)
                        .set(ORDERS.ORDER_DATE, order.getOrderDate())
                        .set(ORDERS.STATUS, order.getStatus().name())
                        .set(ORDERS.USER_ID, order.getUser().getId())
                        .set(ORDERS.TOTAL_PRICE, order.getTotalPrice())
                        .returningResult(ORDERS.ID)
                        .fetchOne())
                .get(ORDERS.ID);

        order.setId(orderId);

        List<IMenuItem> items = order.getItemList();
        if (!items.isEmpty()) {
            List<InsertSetMoreStep<OrdersMenuItemsRecord>> queries = items.stream()
                    .map(item -> dslContext.insertInto(ORDERS_MENU_ITEMS)
                            .set(ORDERS_MENU_ITEMS.ORDER_ID, orderId)
                            .set(ORDERS_MENU_ITEMS.MENU_ITEM_ID, item.getId()))
                    .toList();
            dslContext.batch(queries).execute();
        }
        return order;
    }

    @Override
    public IOrder updateOrder(IOrder order) {
        if (order == null) throw new IllegalArgumentException("order cannot be null");

        int affectedRow = dslContext.update(ORDERS)
                .set(ORDERS.ORDER_DATE, order.getOrderDate())
                .set(ORDERS.STATUS, order.getStatus().name())
                .set(ORDERS.USER_ID, order.getUser().getId())
                .set(ORDERS.TOTAL_PRICE, order.getTotalPrice())
                .where(ORDERS.ID.eq(order.getId()))
                .execute();

        if (affectedRow == 0) throw new RuntimeException("Failed to update Order");
        return order;
    }

    @Override
    public IOrder updateOrderStatus(Long orderId, OrderStatus status) {
        int affectedRow = dslContext.update(ORDERS)
                .set(ORDERS.STATUS, status.name())
                .where(ORDERS.ID.eq(orderId))
                .execute();
        if (affectedRow == 0) throw new RuntimeException("Failed to update OrderStatus");
        return getOrderById(orderId);
    }

    @Override
    public List<IOrder> getOrdersByUser(IUser user) {
        if (user == null) throw new IllegalArgumentException("user cannot be null");

        return fetchOrdersWithJoins(ORDERS.USER_ID.eq(user.getId()));
    }

    @Override
    public List<IOrder> getOrdersByStatus(OrderStatus status) {
        return fetchOrdersWithJoins(ORDERS.STATUS.eq(status.name()));
    }

    protected IOrder getOrderById(Long orderId) {
        if (orderId == null) throw new IllegalArgumentException("orderId cannot be null");

        List<IOrder> orders = fetchOrdersWithJoins(ORDERS.ID.eq(orderId));
        return orders.isEmpty() ? null : orders.get(0);
    }

    private List<IOrder> fetchOrdersWithJoins(Condition condition) {
        return dslContext.select(
                        USERS.ID.as("user_id"),
                        USERS.NAME.as("user_name"),
                        USERS.EMAIL,
                        USERS.PASSWORD,
                        USERS.TELEGRAM,
                        USERS.PHONE,
                        USERS.ADDRESS,
                        ORDERS.ID.as("order_id"),
                        ORDERS.ORDER_DATE,
                        ORDERS.STATUS,
                        ORDERS.TOTAL_PRICE,
                        MENU_ITEMS.ID.as("menu_item_id"),
                        MENU_ITEMS.NAME.as("menu_item_name"),
                        MENU_ITEMS.MENU_CATEGORY,
                        MENU_ITEMS.PRICE,
                        ORDERS_MENU_ITEMS.MENU_ITEM_ID.as("order_menu_item_id"))
                .from(ORDERS)
                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
                .join(MENU_ITEMS).on(MENU_ITEMS.ID.eq(ORDERS_MENU_ITEMS.MENU_ITEM_ID))
                .where(condition)
                .orderBy(ORDERS.ORDER_DATE)
                .fetchGroups(ORDERS_MENU_ITEMS.ORDER_ID)
                .values().stream().map(records -> {
                    IOrder order = null;
                    for (Record record : records) {
                        if (order == null) {
                            order = createOrderFromRecord(record);
                            order.setUser(createUserFromRecord(record));
                            order.setItemList(new ArrayList<>());
                        }
                        order.getItemList().add(createMenuItemFromRecord(record));
                    }
                    return order;
                }).toList();
    }
    private IOrder createOrderFromRecord(Record record) {
        return Order.builder()
                .id(record.getValue("order_id", Long.class))
                .orderDate(record.getValue(ORDERS.ORDER_DATE))
                .status(OrderStatus.valueOf(record.getValue(ORDERS.STATUS)))
                .totalPrice(record.getValue(ORDERS.TOTAL_PRICE))
                .build();
    }

    private IUser createUserFromRecord(Record record) {
        return User.builder()
                .id(record.getValue("user_id", Long.class))
                .name(record.getValue("user_name", String.class))
                .email(record.getValue(USERS.EMAIL))
                .password(record.getValue(USERS.PASSWORD))
                .telegram(record.getValue(USERS.TELEGRAM))
                .phone(record.getValue(USERS.PHONE))
                .address(record.getValue(USERS.ADDRESS))
                .build();
    }

    private IMenuItem createMenuItemFromRecord(Record record) {
        return MenuItem.builder()
                .id(record.getValue("menu_item_id", Long.class))
                .name(record.getValue("menu_item_name", String.class))
                .category(MenuCategory.valueOf(record.getValue(MENU_ITEMS.MENU_CATEGORY)))
                .price(record.getValue(MENU_ITEMS.PRICE))
                .build();
    }
}
