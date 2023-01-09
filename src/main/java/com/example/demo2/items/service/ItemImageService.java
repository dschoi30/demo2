package com.example.demo2.items.service;

import com.example.demo2.common.service.FileService;
import com.example.demo2.items.ItemImage;
import com.example.demo2.items.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@RequiredArgsConstructor
@Transactional
@Service
public class ItemImageService {

    @Value("${itemImageLocation}")
    private String itemImageLocation;

    private final ItemImageRepository itemImageRepository;

    private final FileService fileService;

    public void saveItemImage(ItemImage itemImage, MultipartFile multipartFile) throws Exception {
        String originalImageName = multipartFile.getOriginalFilename();
        String renamedImageName = "";
        String imageUrl = "";

        if(!StringUtils.isEmpty(originalImageName)) {
            renamedImageName = fileService.uploadFile(itemImageLocation, originalImageName, multipartFile.getBytes());
            imageUrl = "/images/items" + renamedImageName;
        }

        itemImage.updateItemImage(originalImageName, renamedImageName, imageUrl);
        itemImageRepository.save(itemImage);
    }

    public void updateItemImage(Long itemId, MultipartFile multipartFile) throws Exception {
        if (!multipartFile.isEmpty()) {
            ItemImage savedItemImage = itemImageRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

            if (!StringUtils.isEmpty(savedItemImage.getRenamedImageName())) {
                fileService.deleteFile(itemImageLocation + "/" + savedItemImage.getRenamedImageName());
            }

            String originalImageName = multipartFile.getOriginalFilename();
            String renamedImageName = fileService.uploadFile(itemImageLocation, originalImageName, multipartFile.getBytes());
            String imageUrl = "/images/items" + renamedImageName;
            savedItemImage.updateItemImage(originalImageName, renamedImageName, imageUrl);
        }
    }
}
