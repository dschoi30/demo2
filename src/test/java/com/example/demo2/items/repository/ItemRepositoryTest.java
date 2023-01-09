package com.example.demo2.items.repository;

import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@Transactional
@SpringBootTest
class ItemRepositoryTest {

    @Autowired EntityManager em;
    JPAQueryFactory queryFactory;
    @Autowired ItemRepository itemRepository;

    public void createItemList() {
        queryFactory = new JPAQueryFactory(em);

        for(int i = 0; i < 10; i++) {
            itemRepository.save(Item.builder()
                            .itemName("item" + i)
                            .description("nice" + i)
                            .price(10000 + i * 100)
                            .stockQuantity(100 + i)
                            .itemSaleStatus(ItemSaleStatus.SELL)
                    .build());
        }
    }

    @Test
    public void findByItemName() {
        this.createItemList();
        Item item1 = itemRepository.findByItemName("item1");
        assertThat(item1).hasFieldOrPropertyWithValue("price", 10100);
    }

    @Test
    public void querydsl_test() {
        this.createItemList();
        QItem qItem = QItem.item;
        List<Item> itemList = queryFactory.selectFrom(qItem)
                .where(
                        qItem.itemSaleStatus.eq(ItemSaleStatus.SELL),
                        qItem.description.like("%" + "nice" + "%"),
                        qItem.price.gt(10500))
                .orderBy(qItem.price.desc())
                .fetch();
        assertThat(itemList)
                .extracting("price")
                .containsExactly(10900, 10800, 10700, 10600);
    }

    @Test
    public void paging_test() {
        this.createItemList();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QItem item = QItem.item;
        String description = "nice";
        int price = 10500;
        String itemSellStatus = "SELL";

        booleanBuilder.and(item.description.like("%" + description + "%"));
        booleanBuilder.and(item.price.gt(price));

        if(StringUtils.equals(itemSellStatus, ItemSaleStatus.SELL)) {
            booleanBuilder.and(item.itemSaleStatus.eq(ItemSaleStatus.SELL));
        }

        Pageable pageable = PageRequest.of(1, 2);
        Page<Item> pagingResult = itemRepository.findAll(booleanBuilder, pageable);
        for (Item itemInfo : pagingResult) {
            System.out.println("item = " + itemInfo.toString());
        }
    }
}