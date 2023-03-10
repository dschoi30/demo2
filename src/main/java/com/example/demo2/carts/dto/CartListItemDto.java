package com.example.demo2.carts.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter @Setter
public class CartListItemDto {

    private Long cartItemId;
    private String itemName;
    @NumberFormat(pattern = "###,###")
    private int price;
    private int count;
    private String imageUrl;

    public CartListItemDto(Long cartItemId, String itemName, int price, int count, String imageUrl) {
        this.cartItemId = cartItemId;
        this.itemName = itemName;
        this.price = price;
        this.count = count;
        this.imageUrl = imageUrl;
    }
}
