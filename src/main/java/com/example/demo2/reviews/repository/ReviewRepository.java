package com.example.demo2.reviews.repository;

import com.example.demo2.reviews.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByItemIdOrderByIdDesc(Long itemId);

    @Query("select r from Review r where r.member.id = :memberId order by r.id desc")
    List<Review> findByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select count(r) from Review r where r.member.id = :memberId")
    Long countReview(@Param("memberId") Long memberId);

}
