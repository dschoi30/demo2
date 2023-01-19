package com.example.demo2.reviews.service;

import com.example.demo2.common.service.FileService;
import com.example.demo2.reviews.ReviewImage;
import com.example.demo2.reviews.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

@RequiredArgsConstructor
@Transactional
@Service
public class ReviewImageService {

    @Value("${reviewImageLocation}")
    private String reviewImageLocation;

    private final ReviewImageRepository reviewImageRepository;
    private final FileService fileService;

    public void saveReviewImage(ReviewImage reviewImage, MultipartFile reviewImageFile) throws Exception {
        String originalReviewImageName = reviewImageFile.getOriginalFilename();
        String reviewImageName = "";
        String reviewImageUrl = "";
        if(!StringUtils.isEmpty(originalReviewImageName)) {
            reviewImageName = fileService.uploadFile(reviewImageLocation, originalReviewImageName, reviewImageFile.getBytes());
            reviewImageUrl = "/images/reviews/" + reviewImageName;
        }
        reviewImage.updateReviewImage(originalReviewImageName, reviewImageName, reviewImageUrl);
        reviewImageRepository.save(reviewImage);
    }

}
