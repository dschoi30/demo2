package com.example.demo2.reviews;

import com.example.demo2.common.BaseTimeEntity;
import com.example.demo2.items.Item;
import com.example.demo2.members.Member;
import com.example.demo2.reviews.dto.ReviewSaveDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private String title;
    private String content;

    @JsonIgnore
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<ReviewImage> reviewImages = new ArrayList<>();

    public static Review createReview(ReviewSaveDto reviewSaveDto, Item item, Member member) {
        return Review.builder()
                .member(member)
                .item(item)
                .title(reviewSaveDto.getTitle())
                .content(reviewSaveDto.getContent())
                .build();
    }
}
