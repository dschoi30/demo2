package com.example.demo2.items;

import com.example.demo2.carts.CartItem;
import com.example.demo2.common.BaseEntity;
import com.example.demo2.exception.OutOfStockException;
import com.example.demo2.items.dto.ItemSaveDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@Entity
public class Item extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    private String itemName;
    @Lob
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemImage> itemImages;

    private int price;
    private int stockQuantity;
    private ItemSaleStatus itemSaleStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItem;

    public void updateItem(ItemSaveDto itemSaveDto) {
        this.itemName = itemSaveDto.getItemName();
        this.description = itemSaveDto.getDescription();
        this.price = itemSaveDto.getPrice();
        this.stockQuantity = itemSaveDto.getStockQuantity();
        this.itemSaleStatus = itemSaveDto.getItemSaleStatus();
    }

    public void deductStock(int count) {
        int restStock = this.stockQuantity - count;
        if(restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다. (현재 재고의 수량 " + this.stockQuantity + ")");
        }
        this.stockQuantity = restStock;
    }

    public void addStock(int count) {
        this.stockQuantity += count;
    }
}
