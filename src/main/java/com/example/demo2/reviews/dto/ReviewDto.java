package com.example.demo2.reviews.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class ReviewDto {

    private Long id;

    private String title;

    private String content;

    private List<ReviewImageDto> reviewImageDtos = new ArrayList<>();
}
