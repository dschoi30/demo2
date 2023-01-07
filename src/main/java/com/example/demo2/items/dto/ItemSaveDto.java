package com.example.demo2.items.dto;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemSaveDto {

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;
    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String description;
    @NotBlank(message = "상품 가격은 필수 입력 값입니다.")
    private int price;
    @NotBlank(message = "상품 판매 수량은 필수 입력 값입니다.")
    private int stockQuantity;
    private ItemSellStatus itemSellStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();
    private List<Long> itemImageIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemSaveDto of(Item item) {
        return modelMapper.map(item, ItemSaveDto.class);
    }
}
