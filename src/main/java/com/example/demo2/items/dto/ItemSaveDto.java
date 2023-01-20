package com.example.demo2.items.dto;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.reviews.dto.ReviewDto;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ItemSaveDto {

    private Long id;

    @NotBlank(message = "상품명은 필수 입력 값입니다.")
    private String itemName;

    @NotBlank(message = "상품 설명은 필수 입력 값입니다.")
    private String description;

    @NumberFormat(pattern = "###,###")
    @NotNull(message = "상품 가격은 필수 입력 값입니다.")
    private Integer price;

    @NotNull(message = "상품 판매 수량은 필수 입력 값입니다.")
    private Integer stockQuantity;

    private ItemSaleStatus itemSaleStatus;

    private List<ItemImageDto> itemImageDtos = new ArrayList<>();

    private List<Long> itemImageIds = new ArrayList<>();

    private List<ReviewDto> reviewDtos = new ArrayList<>();

    private Page<ReviewDto> reviewDtosPage;

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem() {
        return modelMapper.map(this, Item.class);
    }

    public static ItemSaveDto of(Item item) {
        return modelMapper.map(item, ItemSaveDto.class);
    }
}
