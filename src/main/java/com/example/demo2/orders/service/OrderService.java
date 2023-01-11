package com.example.demo2.orders.service;

import com.example.demo2.items.Item;
import com.example.demo2.items.repository.ItemRepository;
import com.example.demo2.members.Member;
import com.example.demo2.members.repository.MemberRepository;
import com.example.demo2.orders.Order;
import com.example.demo2.orders.OrderItem;
import com.example.demo2.orders.dto.OrderDto;
import com.example.demo2.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class OrderService {

    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String userName) {
        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);
        Member member = memberRepository.findByName(userName);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getCount());
        orderItems.add(orderItem);

        Order order = Order.createOrder(member, orderItems);
        orderRepository.save(order);

        return order.getId();
    }
}
