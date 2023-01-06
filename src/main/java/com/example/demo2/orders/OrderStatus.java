package com.example.demo2.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    ORDER("주문 완료"), CANCEL("주문 취소");

    private final String description;
}
