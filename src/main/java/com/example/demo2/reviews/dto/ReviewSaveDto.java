package com.example.demo2.reviews.dto;

import com.example.demo2.items.Item;
import com.example.demo2.members.Member;
import com.example.demo2.reviews.Review;
import com.example.demo2.reviews.ReviewImage;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewSaveDto {

    private Long id;

    private Long itemId;

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String title;

    @NotBlank(message = "리뷰 내용은 필수 입력 값입니다.")
    private String content;

    private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

    private List<Long> reviewImageIds = new ArrayList<>();

    public ReviewSaveDto(Long itemId) {
        this.itemId = itemId;
    }
}
