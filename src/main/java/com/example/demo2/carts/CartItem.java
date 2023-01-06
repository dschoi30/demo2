package com.example.demo2.carts;

import com.example.demo2.items.Item;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class CartItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int count;
}
