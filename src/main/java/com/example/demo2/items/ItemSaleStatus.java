package com.example.demo2.items;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ItemSaleStatus {
    SELL("판매 중"), SOLD_OUT("재고 없음");

    private final String description;
}
