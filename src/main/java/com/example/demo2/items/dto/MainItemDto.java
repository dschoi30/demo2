package com.example.demo2.items.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MainItemDto {

    private Long id;
    private String itemName;
    private String description;
    private String imageUrl;
    private Integer price;

    @QueryProjection
    public MainItemDto(Long id, String itemName, String description, String imageUrl, Integer price) {
        this.id = id;
        this.itemName = itemName;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
    }
}
