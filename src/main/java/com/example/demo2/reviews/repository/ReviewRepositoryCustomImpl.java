//package com.example.demo2.reviews.repository;
//
//import com.example.demo2.reviews.QReview;
//import com.example.demo2.reviews.QReviewImage;
//import com.example.demo2.reviews.Review;
//import com.example.demo2.reviews.dto.ReviewDto;
//import com.querydsl.core.types.dsl.Wildcard;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import javax.persistence.EntityManager;
//import java.util.List;
//
//public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {
//
//    private JPAQueryFactory queryFactory;
//
//    public ReviewRepositoryCustomImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public Page<ReviewDto> getReviewPage(String userName, Pageable pageable) {
//        QReview review = QReview.review;
//        List<Review> content = queryFactory
//                .selectFrom(QReview.review)
//                .where(QReview.review.member.name.like(userName))
//                .orderBy(QReview.review.id.desc())
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = queryFactory.select(Wildcard.count).from(QReview.review)
//                .where(QReview.review.member.name.like(userName))
//                .fetchOne();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//}
