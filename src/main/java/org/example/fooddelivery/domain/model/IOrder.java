package org.example.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface IOrder {
    Long getId();
    void setId(Long id);
    LocalDateTime getOrderDate();
    void setOrderDate(LocalDateTime orderDate);
    OrderStatus getStatus();
    void setStatus(OrderStatus status);
    IUser getUser();
    void setUser(IUser user);
    List<IMenuItem> getItemList();
    void setItemList(List<IMenuItem> itemList);
    BigDecimal getTotalPrice();
    void setTotalPrice(BigDecimal totalPrice);
}
