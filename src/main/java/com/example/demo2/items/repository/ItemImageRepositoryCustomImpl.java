package com.example.demo2.items.repository;

import com.example.demo2.items.ItemImage;
import com.example.demo2.items.QItemImage;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;

import static com.example.demo2.items.QItem.item;
import static com.example.demo2.items.QItemImage.*;

public class ItemImageRepositoryCustomImpl implements ItemImageRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ItemImageRepositoryCustomImpl(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); }

    @Override
    public ItemImage findByItemIdAndIsRepImage(Long itemId, boolean isRepImage) {
        return queryFactory
                .selectFrom(itemImage)
                .join(itemImage.item, item)
                .where(
                        itemImage.item.id.eq(itemId),
                        itemImage.isRepImage.isTrue()
                )
                .fetchOne();
    }
}
