package com.example.demo2.members;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {
    USER("일반 회원"), ADMIN("관리자");

    private final String description;
}
