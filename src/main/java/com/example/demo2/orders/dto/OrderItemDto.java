package com.example.demo2.orders.dto;

import com.example.demo2.orders.OrderItem;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter @Setter
public class OrderItemDto {

    private Long itemId;
    private String itemName;
    private int count;
    @NumberFormat(pattern = "###,###")
    private int orderPrice;
    private String imageUrl;

    public OrderItemDto(OrderItem orderItem, String imageUrl) {
        this.itemId = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getItemName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imageUrl = imageUrl;
    }
}
