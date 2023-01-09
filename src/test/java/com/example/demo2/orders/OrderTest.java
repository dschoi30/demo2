package com.example.demo2.orders;

import com.example.demo2.carts.Cart;
import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.Role;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.orders.repository.OrderItemRepository;
import com.example.demo2.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderTest {

    @Autowired OrderRepository orderRepository;
    @Autowired OrderItemRepository orderItemRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    public Item createItem() {
        return Item.builder()
                .itemName("item1")
                .description("nice")
                .price(10000)
                .stockQuantity(100)
                .itemSaleStatus(ItemSaleStatus.SELL)
                .build();
    }

    public Order createOrder() {

        Member member = Member.builder()
                .name("member1")
                .password("1234")
                .email("test@test.com")
                .role(Role.USER)
                .cart(new Cart())
                .orders(new ArrayList<>())
                .build();
        memberRepository.save(member);

        Order order = Order.builder()
                .member(member)
                .orderItems(new ArrayList<>())
                .orderDate(LocalDateTime.now())
                .orderStatus(OrderStatus.ORDER)
                .build();

        for (int i = 0; i < 3; i++) {
            Item item = createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .order(order)
                    .count(10)
                    .orderPrice(100000)
                    .build();
            orderItemRepository.save(orderItem);
            order.getOrderItems().add(orderItem);
        }
        order.setMember(member);
        orderRepository.save(order);
        return order;
    }

    @Test
    public void cascade_test() {
        Order order = new Order();

        for(int i = 0; i < 3; i++) {
            Item item = this.createItem();
            itemRepository.save(item);
            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .order(order)
                    .count(10)
                    .orderPrice(100000)
                    .build();
            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        em.clear();

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        assertEquals(3, savedOrder.getOrderItems().size());
    }

    @Test
    public void orphanRemoval_test() {
        Order order = this.createOrder();
        order.getOrderItems().remove(0);
        orderRepository.save(order);
        em.flush();
    }
}