package org.example.fooddelivery.data.repoImpls.jooq;

import lombok.RequiredArgsConstructor;
import org.example.fooddelivery.domain.model.*;
import org.example.fooddelivery.domain.repo.DeliveryRepo;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.example.fooddelivery.generated.jooq.Tables.*;

@RequiredArgsConstructor
@Repository("DRwJooq")
public class DeliveryRepoImpl implements DeliveryRepo {

    private final DSLContext dslContext;

    @Override
    public IDelivery saveDelivery(IDelivery delivery) {
        if (delivery == null) throw new IllegalArgumentException("delivery cannot be null");

        Long deliveryId = Objects.requireNonNull(dslContext.insertInto(DELIVERIES)
                        .set(DELIVERIES.ADDRESS, delivery.getAddress())
                        .set(DELIVERIES.PHONE, delivery.getPhone())
                        .set(DELIVERIES.DELIVERY_TIME, delivery.getDeliveryTime())
                        .set(DELIVERIES.ORDER_ID, delivery.getOrder().getId())
                        .returningResult(DELIVERIES.ID)
                        .fetchOne())
                .get(DELIVERIES.ID);

        if (deliveryId == null)
            throw new IllegalArgumentException("Failed to save delivery, no generated ID was found");
        delivery.setId(deliveryId);
        return delivery;
    }

    @Override
    public IDelivery updateDelivery(IDelivery delivery) {
        if (delivery == null) throw new IllegalArgumentException("delivery cannot be null");

        int affectedRow = dslContext.update(DELIVERIES)
                .set(DELIVERIES.ADDRESS, delivery.getAddress())
                .set(DELIVERIES.PHONE, delivery.getPhone())
                .set(DELIVERIES.DELIVERY_TIME, delivery.getDeliveryTime())
                .set(DELIVERIES.ORDER_ID, delivery.getOrder().getId())
                .where(DELIVERIES.ID.eq(delivery.getId()))
                .execute();

        if (affectedRow == 0) throw new RuntimeException("Failed to update delivery");
        return delivery;
    }

    @Override
    public IDelivery getDeliveryById(Long id) {

        List<IDelivery> deliveries = dslContext.select(
                        DELIVERIES.ID.as("delivery_id"),
                        DELIVERIES.DELIVERY_TIME,
                        DELIVERIES.ORDER_ID,
                        ORDERS.ORDER_DATE,
                        ORDERS.STATUS,
                        ORDERS.USER_ID,
                        ORDERS.TOTAL_PRICE,
                        USERS.NAME.as("user_name"),
                        USERS.EMAIL,
                        USERS.PASSWORD,
                        USERS.TELEGRAM,
                        USERS.PHONE,
                        USERS.ADDRESS,
                        ORDERS_MENU_ITEMS.MENU_ITEM_ID,
                        MENU_ITEMS.NAME.as("menu_item_name"),
                        MENU_ITEMS.MENU_CATEGORY,
                        MENU_ITEMS.PRICE)
                .from(DELIVERIES)
                .join(ORDERS).on(DELIVERIES.ORDER_ID.eq(ORDERS.ID))
                .join(USERS).on(ORDERS.USER_ID.eq(USERS.ID))
                .join(ORDERS_MENU_ITEMS).on(ORDERS.ID.eq(ORDERS_MENU_ITEMS.ORDER_ID))
                .join(MENU_ITEMS).on(ORDERS_MENU_ITEMS.MENU_ITEM_ID.eq(MENU_ITEMS.ID))
                .where(DELIVERIES.ID.eq(id))
                .orderBy(DELIVERIES.ID)
                .fetchGroups(DELIVERIES.ID)
                .values().stream().map(records -> {
                    IDelivery delivery = null;
                    for (Record record : records) {
                        if (delivery == null) {
                            delivery = createDeliveryFromRecord(record);
                        }
                        delivery.getOrder().getItemList().add(createMenuItemFromRecord(record));
                    }
                    return delivery;
                }).toList();
        return deliveries.isEmpty() ? null : deliveries.get(0);
    }

    private IMenuItem createMenuItemFromRecord(Record record) {
        return MenuItem.builder()
                .id(record.getValue(ORDERS_MENU_ITEMS.MENU_ITEM_ID))
                .name(record.getValue("menu_item_name", String.class))
                .menuCategory(MenuCategory.valueOf(record.getValue(MENU_ITEMS.MENU_CATEGORY)))
                .price(record.getValue(MENU_ITEMS.PRICE))
                .build();
    }

    private IDelivery createDeliveryFromRecord(Record record) {
        return Delivery.builder()
                .id(record.getValue("delivery_id", Long.class))
                .deliveryTime(record.getValue(DELIVERIES.DELIVERY_TIME))
                .address(record.getValue(USERS.ADDRESS))
                .phone(record.getValue(USERS.PHONE))
                .order(createOrderFromRecord(record))
                .build();
    }

    private IOrder createOrderFromRecord(Record record) {
        return Order.builder()
                .id(record.getValue(DELIVERIES.ORDER_ID))
                .orderDate(record.getValue(ORDERS.ORDER_DATE))
                .status(OrderStatus.valueOf(record.getValue(ORDERS.STATUS)))
                .user(createUserFromRecord(record))
                .itemList(new ArrayList<>())
                .totalPrice(record.getValue(ORDERS.TOTAL_PRICE))
                .build();
    }

    private IUser createUserFromRecord(Record record) {
        return User.builder()
                .id(record.getValue(ORDERS.USER_ID))
                .name(record.getValue("user_name", String.class))
                .email(record.getValue(USERS.EMAIL))
                .password(record.getValue(USERS.PASSWORD))
                .telegram(record.getValue(USERS.TELEGRAM))
                .phone(record.getValue(USERS.PHONE))
                .address(record.getValue(USERS.ADDRESS))
                .build();
    }
}
