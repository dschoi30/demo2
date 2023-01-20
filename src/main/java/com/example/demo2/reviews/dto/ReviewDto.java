package com.example.demo2.reviews.dto;

import com.example.demo2.items.ItemImage;
import com.example.demo2.items.dto.ItemImageDto;
import com.example.demo2.reviews.Review;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class ReviewDto {

    private Long id;
    private String itemName;
    private String reviewer;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();

    public ReviewDto(Review review) {
        this.id = review.getId();
        this.itemName = review.getItem().getItemName();
        this.reviewer = review.getMember().getName();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.createdDate = review.getCreatedDate();
        this.modifiedDate = review.getModifiedDate();
    }

    public void addReviewImageDto(ReviewImageDto reviewImageDto) {
        reviewImageDtos.add(reviewImageDto);
    }

    private static ModelMapper modelMapper = new ModelMapper();
    public static ReviewDto of(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }
}
