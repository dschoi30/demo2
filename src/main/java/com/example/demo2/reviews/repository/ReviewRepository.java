package com.example.demo2.reviews.repository;

import com.example.demo2.reviews.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
