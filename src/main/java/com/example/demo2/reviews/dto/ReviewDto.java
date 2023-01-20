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

    private static ModelMapper modelMapper = new ModelMapper();
    public static ReviewDto of(Review review) {
        return modelMapper.map(review, ReviewDto.class);
    }

    public void addReviewImageDto(ReviewImageDto reviewImageDto) {
        reviewImageDtos.add(reviewImageDto);
    }
}
