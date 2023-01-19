package com.example.demo2.reviews;

import com.example.demo2.common.BaseEntity;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class ReviewImage extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    private String originalReviewImageName;
    private String renamedReviewImageName;
    private String reviewImageUrl;

    public void updateReview(Review review) {
        this.review = review;
    }

    public void updateReviewImage(String originalReviewImageName, String renamedReviewImageName, String reviewImageUrl) {
        this.originalReviewImageName = originalReviewImageName;
        this.renamedReviewImageName = renamedReviewImageName;
        this.reviewImageUrl = reviewImageUrl;
    }
}
