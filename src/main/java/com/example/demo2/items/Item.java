package com.example.demo2.items;

import com.example.demo2.carts.CartItem;
import com.example.demo2.common.BaseEntity;
import com.example.demo2.items.dto.ItemSaveDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
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
    private ItemSellStatus itemSellStatus;

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<CartItem> cartItem;

    public static Item createItem(ItemSaveDto itemSaveDto) {
        return Item.builder()
                .itemName(itemSaveDto.getItemName())
                .description(itemSaveDto.getDescription())
                .price(itemSaveDto.getPrice())
                .stockQuantity(itemSaveDto.getStockQuantity())
                .itemSellStatus(ItemSellStatus.SELL)
                .build();
    }
}
