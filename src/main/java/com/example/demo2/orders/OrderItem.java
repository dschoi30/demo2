package com.example.demo2.orders;

import com.example.demo2.common.BaseEntity;
import com.example.demo2.items.Item;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
public class OrderItem extends BaseEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice;
    private int count;

    public void setOrder(Order order) {
        this.order = order;
    }

    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = OrderItem.builder()
                .item(item)
                .orderPrice(item.getPrice())
                .count(count)
                .build();
        item.deductStock(count);
        return orderItem;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    public void cancel() {
        this.getItem().addStock(count);
    }
}
