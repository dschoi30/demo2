package com.example.demo2.items.repository;

import com.example.demo2.items.Item;
import com.example.demo2.items.dto.MainItemDto;
import com.example.demo2.items.dto.ItemSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {
    Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable);

    Page<MainItemDto> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable);
}
