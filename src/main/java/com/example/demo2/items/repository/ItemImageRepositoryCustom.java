package com.example.demo2.items.repository;

import com.example.demo2.items.ItemImage;

public interface ItemImageRepositoryCustom {
    ItemImage findByItemIdAndIsRepImage(Long itemId, boolean isRepImage);
}
