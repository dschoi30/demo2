package com.example.demo2.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Getter
public enum ItemSellStatus {
    SELL("판매 중"), SOLD_OUT("재고 없음");

    private final String description;
}
