package com.example.demo2.reviews.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.reviews.Review;
import com.example.demo2.reviews.ReviewImage;
import com.example.demo2.reviews.dto.ReviewDto;
import com.example.demo2.reviews.dto.ReviewImageDto;
import com.example.demo2.reviews.dto.ReviewSaveDto;
import com.example.demo2.reviews.repository.ReviewImageRepository;
import com.example.demo2.reviews.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageService reviewImageService;
    private final ReviewImageRepository reviewImageRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long saveReview(ReviewSaveDto reviewSaveDto, List<MultipartFile> reviewImageFiles, String userName) throws Exception {
        Item item = itemRepository.findById(reviewSaveDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByName(userName);
        Review review = Review.createReview(reviewSaveDto, item, member);
        reviewRepository.save(review);

        for (MultipartFile reviewImageFile : reviewImageFiles) {
            ReviewImage reviewImage = new ReviewImage();
            reviewImageService.saveReviewImage(reviewImage, reviewImageFile);
            reviewImage.updateReview(review);
        }

        return review.getId();
    }

    public Page<ReviewDto> getReviewPage(String userName, Pageable pageable) {

        Member member = memberRepository.findByName(userName);
        List<Review> reviews = reviewRepository.findByMemberIdOrderByIdDesc(member.getId(), pageable);
        Long totalCount = reviewRepository.countReview(member.getId());

        List<ReviewDto> reviewDtos = new ArrayList<>();

        for (Review review : reviews) {
            ReviewDto reviewDto = new ReviewDto(review);
            List<ReviewImage> reviewImages = review.getReviewImages();
            for (ReviewImage reviewImage : reviewImages) {
                ReviewImageDto reviewImageDto = new ReviewImageDto(reviewImage);
                reviewDto.addReviewImageDto(reviewImageDto);
            }
            reviewDtos.add(reviewDto);
        }

        return new PageImpl<ReviewDto>(reviewDtos, pageable, totalCount);
    }
}
