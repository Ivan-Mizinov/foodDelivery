package org.example.fooddelivery.domain.model;

import java.time.LocalDateTime;

public interface IDelivery {
    Long getId();
    void setId(Long id);
    String getAddress();
    void setAddress(String address);
    String getPhone();
    void setPhone(String phone);
    LocalDateTime getDeliveryTime();
    void setDeliveryTime(LocalDateTime deliveryTime);
    IOrder getOrder();
    void setOrder(IOrder order);
}
