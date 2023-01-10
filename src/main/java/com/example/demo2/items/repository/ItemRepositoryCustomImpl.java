package com.example.demo2.items.repository;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.QItem;
import com.example.demo2.items.QItemImage;
import com.example.demo2.items.dto.MainItemDto;
import com.example.demo2.items.dto.ItemSearchDto;
import com.example.demo2.items.dto.QMainItemDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.demo2.items.QItem.item;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {
    private JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSaleStatusEq(ItemSaleStatus searchSaleStatus) {
        return searchSaleStatus == null ? null : item.itemSaleStatus.eq(searchSaleStatus);
    }

    private BooleanExpression createdDateAfter(String searchDateType) {
        LocalDateTime localDateTime = LocalDateTime.now();

        if(StringUtils.equals("all", searchDateType) || searchDateType == null) {
            return null;
        } else if(StringUtils.equals("1d", searchDateType)) {
            localDateTime = localDateTime.minusDays(1);
        } else if(StringUtils.equals("1w", searchDateType)) {
            localDateTime = localDateTime.minusWeeks(1);
        } else if(StringUtils.equals("1m", searchDateType)) {
            localDateTime = localDateTime.minusMonths(1);
        } else if(StringUtils.equals("6m", searchDateType)) {
            localDateTime = localDateTime.minusMonths(6);
        }
        return item.createdDate.after(localDateTime);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("itemName", searchBy)) {
            return item.itemName.like("%" + searchQuery + "%");
        } else if(StringUtils.equals("createdBy", searchBy)) {
            return item.createdBy.like("%" + searchQuery + "%");
        }
        return null;
    }

    @Override
    public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {

        List<Item> content = queryFactory
                .selectFrom(item)
                .where(
                        createdDateAfter(itemSearchDto.getSearchDateType()),
                        searchSaleStatusEq(itemSearchDto.getSearchSaleStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                )
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(item)
                .where(
                        createdDateAfter(itemSearchDto.getSearchDateType()),
                        searchSaleStatusEq(itemSearchDto.getSearchSaleStatus()),
                        searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())
                )
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression itemNameLike(String searchQuery) {
        return StringUtils.isEmpty(searchQuery) ? null : item.itemName.like("%" + searchQuery + "%");
    }

    @Override
    public Page<MainItemDto> getItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
        QItem item = QItem.item;
        QItemImage itemImage = QItemImage.itemImage;

        List<MainItemDto> content = queryFactory
                .select(
                        new QMainItemDto(
                                item.id,
                                item.itemName,
                                item.description,
                                itemImage.imageUrl,
                                item.price
                        )
                )
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.isRepImage.isTrue())
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .orderBy(item.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .select(Wildcard.count)
                .from(itemImage)
                .join(itemImage.item, item)
                .where(itemImage.isRepImage.isTrue())
                .where(itemNameLike(itemSearchDto.getSearchQuery()))
                .fetchOne();

        return new PageImpl<>(content, pageable, total);
    }
}
