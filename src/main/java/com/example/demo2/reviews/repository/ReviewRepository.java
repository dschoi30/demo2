package com.example.demo2.reviews.repository;

import com.example.demo2.reviews.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("select r, m.name " +
            "from Review r " +
            "join r.member m " +
            "where r.item.id = :itemId " +
            "order by r.id desc")
    List<Review> findByItemIdOrderByIdDesc(@Param("itemId") Long itemId, Pageable pageable);

    @Query("select r from Review r where r.member.id = :memberId order by r.id desc")
    List<Review> findByMemberIdOrderByIdDesc(@Param("memberId") Long memberId, Pageable pageable);

    @Query("select count(r) from Review r where r.member.id = :memberId")
    Long countReviewByMemberId(@Param("memberId") Long memberId);

    @Query("select count(r) from Review r where r.item.id = :itemId")
    Long countReviewByItemId(@Param("itemId") Long itemId);

}
