package com.example.demo2.items.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.dto.ItemSaveDto;
import com.example.demo2.items.repository.ItemImageRepository;
import com.example.demo2.items.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemServiceTest {

    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired ItemImageRepository itemImageRepository;

    List<MultipartFile> createMultipartFiles() throws Exception {

        List<MultipartFile> multipartFiles = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            String path = "D:/develop/resources";
            String imageName = "image" + i + ".jpg";
            MockMultipartFile mockMultipartFile =
                    new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            multipartFiles.add(mockMultipartFile);
        }
        return multipartFiles;
    }

    @Test
    public void item_saving_test() throws Exception{
        ItemSaveDto itemSaveDto = new ItemSaveDto();
        itemSaveDto.setItemName("item1");
        itemSaveDto.setDescription("nice");
        itemSaveDto.setPrice(10000);
        itemSaveDto.setStockQuantity(100);
        itemSaveDto.setItemSaleStatus(ItemSaleStatus.SELL);

        List<MultipartFile> multipartFiles = createMultipartFiles();
        Long savedItemId = itemService.save(itemSaveDto, multipartFiles);
        List<ItemImage> itemImages = itemImageRepository.findByItemIdOrderByIdAsc(savedItemId);

        Item item = itemRepository.findById(savedItemId).orElseThrow(EntityNotFoundException::new);

        assertEquals(multipartFiles.get(0).getOriginalFilename(), itemImages.get(0).getOriginalImageName());
        assertEquals(itemSaveDto.getItemName(), item.getItemName());
        assertEquals(itemSaveDto.getDescription(), item.getDescription());
    }

}