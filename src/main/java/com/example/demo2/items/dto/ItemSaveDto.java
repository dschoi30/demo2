package com.example.demo2.items.dto;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSellStatus;
import lombok.Getter;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter
public class ItemSaveDto {
    private String itemName;
    @Lob
    private String description;
    private int price;
    private int stockQuantity;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private ItemSellStatus itemSellStatus;

    public Item toEntity() {
        return Item.builder()
                .itemName(itemName)
                .price(price)
                .stockQuantity(stockQuantity)
                .regDate(regDate)
                .updateDate(updateDate)
                .itemSellStatus(itemSellStatus)
                .build();
    }
}
