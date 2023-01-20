package com.example.demo2.reviews.dto;

import com.example.demo2.reviews.ReviewImage;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewImageDto {

    private Long id;
    private String originalReviewImageName;
    private String renamedReviewImageName;
    private String reviewImageUrl;

    public ReviewImageDto(ReviewImage reviewImage) {
        this.id = reviewImage.getId();
        this.originalReviewImageName = reviewImage.getOriginalReviewImageName();
        this.renamedReviewImageName = reviewImage.getRenamedReviewImageName();
        this.reviewImageUrl = reviewImage.getReviewImageUrl();
    }
}
