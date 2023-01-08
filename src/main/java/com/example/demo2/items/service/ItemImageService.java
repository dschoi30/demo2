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
        String imageName = "";
        String imageUrl = "";

        if(!StringUtils.isEmpty(originalImageName)) {
            imageName = fileService.uploadFile(itemImageLocation, originalImageName, multipartFile.getBytes());
            imageUrl = "/images/items" + imageName;
        }

        itemImage.updateItemImage(imageName, originalImageName, imageUrl);
        itemImageRepository.save(itemImage);
    }
}
