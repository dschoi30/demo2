package com.example.demo2.items.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public Long save(ItemSaveDto itemSaveDto) {
        return itemRepository.save(itemSaveDto.toEntity()).getId();
    }

    List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }


}
