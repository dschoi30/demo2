package com.example.demo2.items;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.time.LocalDateTime;

@ToString
@RequiredArgsConstructor
@Getter
@Entity
public class Item {

    @Id @GeneratedValue
    private Long id;

    private String itemName;
    @Lob
    private String description;
    private int price;
    private int stockQuantity;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
    private ItemSellStatus itemSellStatus;

    @Builder
    public Item(String itemName, String description, int price, int stockQuantity, LocalDateTime regDate, LocalDateTime updateDate, ItemSellStatus itemSellStatus) {
        this.itemName = itemName;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.regDate = regDate;
        this.updateDate = updateDate;
        this.itemSellStatus = itemSellStatus;
    }
}
