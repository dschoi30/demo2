package com.example.demo2.reviews.dto;

import com.example.demo2.reviews.ReviewImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
@Getter @Setter
public class ReviewImageDto {

    private Long id;
    private String originalReviewImageName;
    private String renamedReviewImageName;
    private String reviewImageUrl;

    private static ModelMapper modelMapper = new ModelMapper();
    public static ReviewImageDto of(ReviewImage reviewImage) {
        return modelMapper.map(reviewImage, ReviewImageDto.class);
    }
}
