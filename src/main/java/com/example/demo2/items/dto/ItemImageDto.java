package com.example.demo2.items.dto;

import com.example.demo2.items.ItemImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemImageDto {

    private Long id;
    private String imageName;
    private String originalImageName;
    private String imageUrl;
    private boolean isRepImage;

    private static ModelMapper modelMapper = new ModelMapper();
    private static ItemImageDto of(ItemImage itemImage) {
        return modelMapper.map(itemImage, ItemImageDto.class);
    }
}
