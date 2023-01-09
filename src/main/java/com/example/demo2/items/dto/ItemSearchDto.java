package com.example.demo2.items.dto;

import com.example.demo2.items.ItemSaleStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {

    private String searchDateType;
    private ItemSaleStatus searchSaleStatus;
    private String searchBy;
    private String searchQuery = "";
}
