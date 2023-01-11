package com.example.demo2.orders.service;

import com.example.demo2.carts.Cart;
import com.example.demo2.items.Item;
import com.example.demo2.items.ItemSaleStatus;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.Role;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.orders.Order;
import com.example.demo2.orders.OrderItem;
import com.example.demo2.orders.dto.OrderDto;
import com.example.demo2.orders.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired MemberRepository memberRepository;

    public Item createItem() {
        return Item.builder()
                .itemName("item1")
                .description("nice")
                .price(10000)
                .stockQuantity(100)
                .itemSaleStatus(ItemSaleStatus.SELL)
                .build();
    }

    public Member createMember() {
        return Member.builder()
                .name("member1")
                .password("1234")
                .email("test@test.com")
                .role(Role.USER)
                .cart(new Cart())
                .orders(new ArrayList<>())
                .build();
    }

    @Test
    public void order_test() {
        Item item = itemRepository.save(this.createItem());
        Member member = memberRepository.save(this.createMember());

        OrderDto orderDto = new OrderDto();
        orderDto.setCount(1);
        orderDto.setItemId(item.getId());

        Long orderId = orderService.order(orderDto, member.getName());
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();
        int totalPrice = orderDto.getCount() * item.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());
    }
}