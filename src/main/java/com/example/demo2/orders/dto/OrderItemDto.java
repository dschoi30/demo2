package com.example.demo2.orders.dto;

import com.example.demo2.orders.OrderItem;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderItemDto {

    private String itemName;
    private int count;
    private int orderPrice;
    private String imageUrl;

    public OrderItemDto(OrderItem orderItem, String imageUrl) {
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imageUrl = imageUrl;
    }
}
