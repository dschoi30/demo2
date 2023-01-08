package com.example.demo2.items.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.repository.ItemImageRepository;
import com.example.demo2.items.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;

    public Long save(ItemSaveDto itemSaveDto, List<MultipartFile> itemImageFiles) throws Exception {
        Item item = itemSaveDto.createItem();
        itemRepository.save(item);

        for(int i = 0; i < itemImageFiles.size(); i++) {
            ItemImage itemImage = new ItemImage();
            itemImage.modifyItem(item);

            if(i == 0) {
                itemImage.isRepImage();
            }
            itemImageService.saveItemImage(itemImage, itemImageFiles.get(i));
        }
        return item.getId();
    }

    List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Item findById(Long id) {
        return itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다."));
    }


}
