package com.example.demo2.reviews.repository;

import com.example.demo2.reviews.ReviewImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewImageRepository extends JpaRepository<ReviewImage, Long> {
    List<ReviewImage> findByReviewIdOrderByIdAsc(Long reviewId);
}
