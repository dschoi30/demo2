package com.example.demo2.items.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.dto.ItemImageDto;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.repository.ItemImageRepository;
import com.example.demo2.items.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemImageService itemImageService;
    private final ItemImageRepository itemImageRepository;

    @Transactional
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

    public ItemSaveDto getItemDetail(Long itemId) {
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtos = new ArrayList<>();
        for(ItemImage itemImage : itemImages) {
            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtos.add(itemImageDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemSaveDto itemSaveDto = ItemSaveDto.of(item);
        itemSaveDto.setItemImageDtos(itemImageDtos);
        return itemSaveDto;
    }

    @Transactional
    public Long updateItem(ItemSaveDto itemSaveDto, List<MultipartFile> itemImageFiles) throws Exception {
        Item item = itemRepository.findById(itemSaveDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemSaveDto);

        List<Long> itemImageIds = itemSaveDto.getItemImageIds();

        for(int i = 0; i < itemImageFiles.size(); i++) {
            itemImageService.updateItemImage(itemImageIds.get(i), itemImageFiles.get(i));
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
