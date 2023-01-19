package com.example.demo2.reviews.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewImageDto {

    private Long id;
    private String originalReviewImageName;
    private String renamedReviewImageName;
    private String reviewImageUrl;
}
